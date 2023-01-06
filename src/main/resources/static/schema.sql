CREATE TABLE IF NOT EXISTS USERS (
	user_id					INT				AUTO_INCREMENT		PRIMARY KEY,
	email					VARCHAR(320)	NOT NULL,
	pass_hash				VARCHAR(256)	NOT NULL,
	salt					VARCHAR(64)		NOT NULL
);

CREATE TABLE IF NOT EXISTS ANALYSES (
	analysis_id				BIGINT			AUTO_INCREMENT		PRIMARY KEY,
	user_id					INT				NOT NULL,
	likes					INT				NOT NULL			DEFAULT 0,
	completion_timestamp	DATE			NOT NULL,
	CONSTRAINT FK_analyses_user	FOREIGN KEY (user_id) REFERENCES USERS(user_id)
);

CREATE TABLE IF NOT EXISTS RESULTS (
    result_id 				BIGINT     		AUTO_INCREMENT   	PRIMARY KEY,
	analysis_id				INT				NOT NULL,
	site_id					INT				NOT NULL,
    search_term				VARCHAR(250)	NOT NULL,
    sentiment_average		TINYINT			NOT NULL,
    sentiment_confidence	TINYINT			NOT NULL,
	CONSTRAINT FK_results_analysis FOREIGN KEY (analysis_id) REFERENCES ANALYSES(analysis_id)
	CONSTRAINT FK_results_site FOREIGN KEY (site_id) REFERENCES SITES(site_id)
);

CREATE TABLE IF NOT EXISTS SITES (
	site_id					INT				AUTO_INCREMENT		PRIMARY KEY,
	site_name				VARCHAR(100)	NOT NULL,
	site_domain				VARCHAR(256)	NOT NULL
);

CREATE TABLE IF NOT EXISTS OCCURRENCES (
	occurrence_id			BIGINT			AUTO_INCREMENT		PRIMARY KEY,
	result_id				INT				NOT NULL,
	occurrence_extract		VARCHAR(300)	NOT NULL,
	occurrence_path			VARCHAR(512)	NOT NULL,
	sentiment				TINYINT			NOT NULL
	CONSTRAINT FK_occurrences_result FOREIGN KEY (result_id) REFERENCES RESULTS(result_id)
)