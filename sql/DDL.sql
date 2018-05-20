--- 사용자 로그인 테이블 ---
create table user_tbl(
    id character varying primary key,
    pwd character varying not null
);

--- 과목 테이블 ---
create table subject_tbl(
    yns character varying,
    name character varying,
    day character varying,
    time character varying,
    professor character varying
);

--- TODO 테이블 ---
create table todo_tbl(
    importance integer,
    name character varying,
    deadline character varying,
    rdeadline character varying,
    state integer,
    wtd text,
    memo text    
);