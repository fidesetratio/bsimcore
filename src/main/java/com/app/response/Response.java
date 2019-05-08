package com.app.response;

public class Response {
	
		private String message;
		private String result;
		private Object data;
		
		
		
		
		public Response(String message, String result, Object data) {
			super();
			this.message = message;
			this.result = result;
			this.data = data;
		}
		
		
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		}
		public Object getData() {
			return data;
		}
		public void setData(Object data) {
			this.data = data;
		}

}
