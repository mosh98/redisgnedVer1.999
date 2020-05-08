package com.userRed.redesigned.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class WasteBinRequest {

	@NonNull
	Double latitude;
	@NonNull
	Double longitude;
	@NonNull
	Double maxDistance;

}
