CREATE TABLE scheduled_message
(
    id             BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    description    VARCHAR(10) NOT NULL,
    scheduled_date DATE        NOT NULL,
    sent           BOOLEAN     NOT NULL,
    sent_at        DATETIME    NULL
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '예약 메세지';

INSERT INTO scheduled_message (id, description, scheduled_date, sent, sent_at)
VALUES
    (1, '메세지1', '2023-04-01', false, null),
    (2, '메세지2', '2023-04-01', false, null),
    (3, '메세지3', '2023-04-01', false, null),
    (4, '메세지4', '2023-04-01', false, null),
    (5, '메세지5', '2023-04-01', false, null);
