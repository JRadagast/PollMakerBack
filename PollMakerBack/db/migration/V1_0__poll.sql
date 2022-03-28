CREATE TABLE IF NOT EXISTS poll (
    idpoll INT NOT NULL DEFAULT nextval('"Poll_idpoll_seq"'::regclass),
    title VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    iduser INT,
    requiresauthentication boolean NOT NULL DEFAULT false
);

CREATE TABLE polloptions (
	idpolloptions INT NOT NULL DEFAULT nextval('"PollOptions_idpolloptions_seq"'::regclass),
	idpoll INT NOT NULL,
	option VARCHAR(255) NOT NULL,
	situacao INT NOT NULL DEFAULT '1'
);

CREATE TABLE pollanswers (
	idpollanswer INT NOT NULL DEFAULT nextval('"PollAnswers_idpollanswer_seq"'::regclass),
	iduser INT NULL,
	idpoll INT NOT NULL,
	idpolloption INT NOT NULL
)
;

CREATE TABLE usuario (
	idusuario INT NOT NULL DEFAULT nextval('usuario2_idusuario_seq'::regclass),
	login VARCHAR(50) NOT NULL,
	password VARCHAR(255) NOT NULL
)
;
