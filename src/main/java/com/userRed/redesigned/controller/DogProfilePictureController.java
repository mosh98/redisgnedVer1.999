package com.userRed.redesigned.controller;

import com.userRed.redesigned.service.DogProfilePictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
@RequestMapping("/dogPicture")
public class DogProfilePictureController {

    @Autowired
    private DogProfilePictureService dogProfilePictureService;


    /**CALL: localhost:8080/dogPicture/addPicture?id=XXXXX
     * I bodyn skickas det med en RequestPart som är en fil med namnet file  */
    @PutMapping(path = "addPicture", params = "id")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file, @RequestParam("id") Long id)
    {
        dogProfilePictureService.uploadDogProfilePictureById(id, file, true);
        return "File [" + file.getOriginalFilename() + "] uploaded to storage";
    }

    /**CALL: localhost:8080/dogPicture/getPicture?id=XXXXX */

    @GetMapping(path = "getPicture", params = "id")
    @ResponseBody
    public URL getPictureFromId(@RequestParam(value = "id") Long id) throws MalformedURLException {
        return dogProfilePictureService.getProfilePictureFromDogById(id);
    }

/*ONÖDIGA CONTROLLERS?

    @PostMapping(path = "addDir")
    public Map<String, String> uploadDir(@RequestParam(value = "folderName") String folderName)
    {
        this.amazonS3ClientService.createFolder(folderName);
        Map<String, String> response = new HashMap<>();
        response.put("message", "file [" + folderName + "] uploading request submitted successfully.");
        return response;
    }


   /* @DeleteMapping(path = "delete")
    public Map<String, String> deleteFile(@RequestParam("file_name") String fileName)
    {
        this.amazonS3ClientService.deleteFileFromS3Bucket(fileName);
        Map<String, String> response = new HashMap<>();
        response.put("message", "file [" + fileName + "] removing request submitted successfully.");
        return response;
}

    /*@DeleteMapping(path = "dir")
    public Map<String, String> deleteDirectory(@RequestParam("file_name") String fileName)
    {
        this.amazonS3ClientService.deleteDirectory(fileName);
         Map<String, String> response = new HashMap<>();
        response.put("message", "file [" + fileName + "] removing request submitted successfully.");
        return response;
    }
*/

}