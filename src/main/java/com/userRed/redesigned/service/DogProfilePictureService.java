package com.userRed.redesigned.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.userRed.redesigned.model.Dog;
import com.userRed.redesigned.repository.DogRepository;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@Service
public class DogProfilePictureService
{
    private String awsS3AudioBucket;
    private AmazonS3 amazonS3;

    @Autowired
    private DogRepository dogRepository;
    private static final Logger logger = LoggerFactory.getLogger(DogProfilePictureService.class);

    @Autowired
    public DogProfilePictureService(Region awsRegion, AWSCredentialsProvider awsCredentialsProvider, String awsS3AudioBucket)
    {
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(awsRegion.getName()).build();
        this.awsS3AudioBucket = awsS3AudioBucket;
    }

    public String getProfilePictureFromDogById(Long id) {

        Optional<Dog> dog = dogRepository.findByDogId(id);
        return dog.get().getProfile_picture();
    }

    public void uploadDogProfilePictureById(Long id, MultipartFile multipartFile, boolean enablePublicReadAccess)
    {

        String fileName = multipartFile.getOriginalFilename();

        try {
            //creating the file in the server (temporarily)
            File file = new File(fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fileName = "dogAvatarID" + id.toString() + "." + FilenameUtils.getExtension(fileName);
            fos.close();
            PutObjectRequest putObjectRequest = new PutObjectRequest(this.awsS3AudioBucket, fileName, file);

            //Get actual dog from ID
            Optional<Dog> dog = dogRepository.findByDogId(id);

            //Without this line of codes I get a null pointer exception, so yeah..
            if(dog.get().getProfile_picture() == null || dog.get().getProfile_picture().isEmpty()) {
                dog.get().setProfile_picture("https://dog-profile-pictures.s3.amazonaws.com/" + fileName);
                dogRepository.save(dog.get());
            }

            //Overwrite profile picture in the database
            else if(!dog.get().getProfile_picture().isEmpty()) {
                String[] parts = dog.get().getProfile_picture().split("/");
                deleteOldProfilePicture(parts[3]);
                dog.get().setProfile_picture("https://dog-profile-pictures.s3.amazonaws.com/" + fileName);
                dogRepository.save(dog.get());
            }

            if (enablePublicReadAccess) {
                putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
            }
            this.amazonS3.putObject(putObjectRequest);

            //removing the file created in the server
            file.delete();
        } catch (IOException | AmazonServiceException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while uploading [" + fileName + "] ");
        }
    }


    public void deleteOldProfilePicture(String fileName)
    {
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(awsS3AudioBucket, fileName));
        } catch (AmazonServiceException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while removing [" + fileName + "] ");
        }
    }

/*KANSKE ONÖDIGA METODER?

     public void deleteDirectory(String prefix) {
        ObjectListing objectList = this.amazonS3.listObjects( this.awsS3AudioBucket, prefix );
        List<S3ObjectSummary> objectSummeryList =  objectList.getObjectSummaries();
        String[] keysList = new String[ objectSummeryList.size() ];
        int count = 0;
        for( S3ObjectSummary summery : objectSummeryList ) {
            keysList[count++] = summery.getKey();
        }
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest( this.awsS3AudioBucket ).withKeys( keysList );
        this.amazonS3.deleteObjects(deleteObjectsRequest);
    }

    public void createFolder(String folderName) {
        // create meta-data for your folder and set content-length to 0
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);

        // create empty content
        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);

        // create a PutObjectRequest passing the folder name suffixed by /
        PutObjectRequest putObjectRequest = new PutObjectRequest(this.awsS3AudioBucket,
                folderName + "/", emptyContent, metadata);

        // send request to S3 to create folder
        amazonS3.putObject(putObjectRequest);
    }
*/
}