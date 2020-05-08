package com.userRed.redesigned.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class WasteBinRequest {

	Double latitude;
	Double longitude;
	Double maxDistance;

}
