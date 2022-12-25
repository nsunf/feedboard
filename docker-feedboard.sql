drop table member;
drop table posts;
drop table comments;
drop table images;
drop table likes;

commit;

create table members (
    member_uuid char(36) primary key,
    member_id varchar(13) unique not null,
    member_ssn varchar(13) unique not null,
    member_pw varchar(13) not null,
    member_name varchar(25) not null,
    member_phone char(13) not null,
    member_regdate date default sysdate not null,
    member_image varchar(300)
);

create table posts (
    post_id char(36) primary key,
    member_uuid char(36) not null,
    post_content varchar2(4000),
    post_regdate date default sysdate not null,
    post_editdate date,
    constraint fk_posts_member foreign key (member_uuid) references members(member_uuid)
);

create table comments (
    comment_id char(36) primary key,
    post_id char(36) not null,
    member_uuid char(36) not null,
    comment_content long,
    comment_regdate date default sysdate not null,
    comment_editdate date,
    constraint fk_comments_post foreign key (post_id) references posts(post_id),
    constraint fk_comments_member foreign key (member_uuid) references members(member_uuid)
);

create table likes (
    post_id char(36),
    member_uuid char(36),
    like_regdate date default sysdate not null,
    constraint pk_likes primary key(post_id, member_uuid),
    constraint fk_likes_posts foreign key (post_id) references posts(post_id),
    constraint fk_likes_members foreign key (member_uuid) references members(member_uuid)
);

create table images (
    image_id char(36) primary key,
    post_id char(36) not null,
    image_order number not null,
    image_ext varchar(7) not null,
    constraint fk_images_post foreign key (post_id) references posts(post_id)
);

insert into members values (
    '54c453ad-347a-4c47-b069-d64031c80131',
    'yjs1017',
    '9710171234567',
    'sys1234',
    '양준수',
    '010-5013-5657',
    default,
    null
);

insert into members values (
    'fe4559b7-aabc-4838-8cc8-324d3bf38c47',
    'user1234',
    '9710172345671',
    'sys1234',
    '양준목',
    '010-1234-5678',
    default,
    null
);

insert into posts values (
    '9642ed0d-86df-4ae2-8b62-63a85525ad18',
    '54c453ad-347a-4c47-b069-d64031c80131',
    'Praesent sed faucibus tortor, a sodales magna. Nullam eget gravida ante. Nunc in viverra nisi. Nam feugiat mollis nulla a varius. Nam ac odio vel purus sodales luctus. Vivamus sit amet urna ac urna pellentesque venenatis.',
    to_date('2022/12/12/12:11:00', 'YYYY/MM/DD/HH24:MI:SS'),
    null
);

insert into posts values (
    '770636bc-a1d1-4b30-8144-8b35d290e2f1',
    '54c453ad-347a-4c47-b069-d64031c80131',
    'Quisque mollis molestie quam et gravida. Morbi dictum justo a aliquam maximus. Aenean viverra finibus nunc ac lobortis. Sed a nisi vitae sem viverra bibendum.',
    to_date('2022/12/14/16:10:00', 'YYYY/MM/DD/HH24:MI:SS'),
    null
);

insert into posts values (
    '39728bdf-4b7f-440e-a25a-97295d05c2a6',
    'fe4559b7-aabc-4838-8cc8-324d3bf38c47',
    'Nam id tempus lacus. Donec eget congue enim. Sed a ultricies dui, quis sagittis tellus. Suspendisse vestibulum vel ipsum eget pellentesque. Pellentesque sit amet ex ac nibh mattis faucibus.',
    to_date('2022/12/20/20:32:00', 'YYYY/MM/DD/HH24:MI:SS'),
    null
);

insert into comments values (
    '72a90594-6bf0-40a7-8c80-d4c5c30d03d3',
    '9642ed0d-86df-4ae2-8b62-63a85525ad18',
    '54c453ad-347a-4c47-b069-d64031c80131',
    'Nullam odio lacus, porttitor rutrum arcu vitae, eleifend dapibus sem.',
    to_date('2022/12/12/14:33:00', 'YYYY/MM/DD/HH24:MI:SS'),
    null
);

insert into comments values (
    '67413ad0-2028-42c2-94a2-2d04a9819639',
    '770636bc-a1d1-4b30-8144-8b35d290e2f1',
    '54c453ad-347a-4c47-b069-d64031c80131',
    'Nullam odio lacus, porttitor rutrum arcu vitae, eleifend dapibus sem.',
    to_date('2022/12/15/09:27:00', 'YYYY/MM/DD/HH24:MI:SS'),
    null
);

insert into comments values (
    '3bb334cb-053a-4de7-9d4e-db63cd2bcb7c',
    '770636bc-a1d1-4b30-8144-8b35d290e2f1',
    'fe4559b7-aabc-4838-8cc8-324d3bf38c47',
    'Aliquam maximus nisi at venenatis finibus. Vestibulum maximus facilisis augue. Nunc vel ipsum ac tellus dignissim blandit. Suspendisse fringilla in magna in ultricies.',
    to_date('2022/12/16/11:43:00', 'YYYY/MM/DD/HH24:MI:SS'),
    null
);

insert into likes values (
    '9642ed0d-86df-4ae2-8b62-63a85525ad18',
    '54c453ad-347a-4c47-b069-d64031c80131',
    to_date('2022/12/13/12:45:00', 'YYYY/MM/DD/HH24:MI:SS')
);

insert into likes values (
    '770636bc-a1d1-4b30-8144-8b35d290e2f1',
    '54c453ad-347a-4c47-b069-d64031c80131',
    to_date('2022/12/15/14:32:00', 'YYYY/MM/DD/HH24:MI:SS')
);

insert into likes values (
    '39728bdf-4b7f-440e-a25a-97295d05c2a6',
    '54c453ad-347a-4c47-b069-d64031c80131',
    to_date('2022/12/21/15:01:00', 'YYYY/MM/DD/HH24:MI:SS')
);

insert into likes values (
    '770636bc-a1d1-4b30-8144-8b35d290e2f1',
    'fe4559b7-aabc-4838-8cc8-324d3bf38c47',
    to_date('2022/12/16/15:41:00', 'YYYY/MM/DD/HH24:MI:SS')
);

insert into likes values (
    '39728bdf-4b7f-440e-a25a-97295d05c2a6',
    'fe4559b7-aabc-4838-8cc8-324d3bf38c47',
    to_date('2022/12/22/22:11:00', 'YYYY/MM/DD/HH24:MI:SS')
);

insert into images values (
    'a2307ec9-70c9-4745-9578-c41798ad7fac',
    '9642ed0d-86df-4ae2-8b62-63a85525ad18',
    1,
    '.jpg'
);

insert into images values (
    '7f600c08-1a23-4e0b-92ae-9065003c01cd',
    '9642ed0d-86df-4ae2-8b62-63a85525ad18',
    2,
    '.jpg'
);

insert into images values (
    'ff42adfe-d24d-4ba6-ad35-ef579518e0df',
    '9642ed0d-86df-4ae2-8b62-63a85525ad18',
    3,
    '.jpg'
);

select post_id, rownum from posts;

select 
    c.comment_id, 
    c.post_id, 
    c.member_uuid, 
    m.member_id, 
    c.comment_content,
    to_char(c.comment_regdate, 'YYYY/MM/DD HH24:MI:SS'),
    to_char(c.comment_editdate, 'YYYY/MM/DD HH24:MI:SS')
from comments c
join members m
on c.member_uuid = m.member_uuid;

select i.image_id||i.image_ext from images i where i.post_id = '9642ed0d-86df-4ae2-8b62-63a85525ad18' order by i.image_order;

select
    p.post_id,
    m.member_uuid,
    m.member_id,
    p.post_content,
    p.post_regdate,
    count(l.member_uuid) over(partition by p.post_id)
from posts p
join members m
on p.member_uuid = m.member_uuid
join likes l
on l.member_uuid = m.member_uuid;

select
    p.post_id,
    m.member_uuid,
    m.member_id,
    p.post_content,
    to_char(p.post_regdate, 'YYYY/MM/DD HH:mm') as regdate,
    to_char(p.post_editdate, 'YYYY/MM/DD HH:mm') as editdate,
    count(l.member_uuid) as likes_cnt,
    count(c.comment_id) as comm_cnt
from posts p
left join members m
on p.member_uuid = m.member_uuid
left join likes l
on p.post_id = l.post_id
left join comments c
on p.post_id = c.post_id
group by (p.post_id, m.member_uuid, m.member_id, p.post_content, p.post_regdate, p.post_editdate)
order by regdate desc;

select p.post_id, count(c.comment_id) from posts p left join comments c on p.post_id = c.post_id group by p.post_id;

select
    mm.post_id, mm.member_uuid, mm.member_id, mm.post_content, mm.regdate, mm.editdate, c.post_id,
    count(l.member_uuid) as likes_cnt
    --count(c.comment_id) as comm_cnt
from (select p.post_id, m.member_uuid, m.member_id, p.post_content, to_char(p.post_regdate, 'YYYY/MM/DD HH:mm') as regdate, to_char(p.post_editdate, 'YYYY/MM/DD HH:mm') as editdate from posts p left join members m on p.member_uuid = m.member_uuid) mm
left join likes l
on mm.post_id = l.post_id
left join comments c
on mm.post_id = c.post_id
group by (mm.post_id, mm.member_uuid, mm.member_id, mm.post_content, mm.regdate, mm.editdate, c.post_id)
;
delete from posts;
delete from comments;
delete from likes;
delete from images;