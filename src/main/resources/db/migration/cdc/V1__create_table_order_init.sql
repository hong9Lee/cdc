create table orders
(
    seq           bigint auto_increment comment '주문 시퀀스' primary key,
    order_id      varchar(64) not null comment '주문 ID',
    order_status  varchar(64) not null comment '주문 상태',
    reg_date_time datetime(6) null comment '등록일자',
    mod_date_time datetime(6) null comment '수정일자'
) comment '주문 테이블';

