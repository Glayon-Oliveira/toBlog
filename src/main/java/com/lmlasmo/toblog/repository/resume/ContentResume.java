package com.lmlasmo.toblog.repository.resume;

public interface ContentResume {

	public interface ContentIdResume{
		public Long getId();
	}

	public interface ContentBodyResume {
		public String getBody();
	}

	public interface ContentIdOrderResume extends ContentIdResume{
		public Long getOrderIndex();
	}

	public interface ContentIdOrderTypeResume extends ContentIdOrderResume{
		public String getType();
	}

}
