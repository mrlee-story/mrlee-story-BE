package kr.co.mrlee.story.common;

public enum ResponseStatus {
	// HTTP Status 200
    SUCCESS(200, "SU", "Success."),
    
    // HTTP Status 400
    VALIDATION_FAILED(400, "VF", "Validation failed."),
    DUPLICATE_EMAIL (400, "DE", "Duplicate email."),
    DUPLICATE_NICKNAME (400, "DN",  "Duplicate nickname."),
    DUPLICATE_TEL_NUMBER(400, "DT", "Duplicate tel number."),
    NOT_EXISTED_USER(400, "NU", "This user does not exist."),
    NOT_EXISTED_BOARD(400, "NB", "This board does not exist."),
    
    // HTTP Status 401
    SIGN_IN_FAIL(401, "SF", "Login information mismatch."),
    AUTHORIZATION_FAIL(401, "AF", "Authorization Failed."),
    
    // HTTP Status 403
    NO_PERMISSION(403, "NP", "Do not have permission."),
    
    // HTTP Status 500
    DATABASE_ERROR(500, "DBE", "Database error.");
	
	private int httpStatusCode;
	private String responseStatusCode;
	private String responseStatusMessage;
	
	private ResponseStatus(int httpStatusCode, String responseStatusCode, String responseStatusMessage) {
		this.httpStatusCode = httpStatusCode;
		this.responseStatusCode = responseStatusCode;
		this.responseStatusMessage = responseStatusMessage;
	}
	
	public int getHttpStatus() {
		return this.httpStatusCode;
	}
	
	public String getResponseCode() {
		return this.responseStatusCode;
	}
	
	public String getResponseMessage() {
		return this.responseStatusMessage;
	}
}
