drop table ticket;
drop table price;
drop table shedule;

CREATE TABLE film(
                     id numeric(10) identity,
                     name varchar(255) not null,
                     duration numeric(3) not null,

                     CONSTRAINT PK_films_id PRIMARY KEY CLUSTERED (id)
);

create table shedule(
                        id numeric(10) identity,
                        film_id numeric(10) not null,
                        start_at datetime not null

                            CONSTRAINT PK_shedule_id PRIMARY KEY CLUSTERED (id),
                        CONSTRAINT FK_shedule__film FOREIGN KEY (film_id)  REFERENCES film (id)
);

create table price(
                      id numeric(10) identity,
                      shedule_id numeric(10) not null,
                      price numeric(6,2) not null

                          CONSTRAINT PK_price_id PRIMARY KEY CLUSTERED (id),
                      CONSTRAINT FK_price__shedule FOREIGN KEY (shedule_id)  REFERENCES shedule (id)
);


create table ticket(
                       id numeric(10) identity,
                       price_id numeric(10) not null,
                       number varchar(255) not null

                           CONSTRAINT PK_ticket_id PRIMARY KEY CLUSTERED (id),
                       CONSTRAINT FK_titket__price FOREIGN KEY (price_id)  REFERENCES price (id)
);

insert into film(name, duration)
values
('Lord Of The Rings', 180),
('Terminator V', 120),
('Star Wars', 90),
('Home Alone', 90),
('Harry Potter', 120)
;

insert into shedule(
    film_id,
    start_at)
values
(1,'2021-06-01 08:00'),
(1,'2021-06-01 10:00'),
(2,'2021-06-01 12:00'),
(3,'2021-06-01 14:00'),
(5,'2021-06-01 17:00'),
(4,'2021-06-01 20:00'),
(1,'2021-06-01 21:40'),
(2,'2021-06-01 23:50')
;

insert into price(
    shedule_id,
    price)
values
(1,	150),
(2, 150),
(3, 200),
(4, 200),
(5, 300),
(6, 700),
(7, 700),
(8, 500)
;

insert into ticket(
    price_id,
    number)
values
(1,'1'),
(1,'2'),
(1,'3'),
(2,'1'),
(2,'2'),
(3,'1'),
(3,'2'),
(4,'1'),
(4,'2'),
(4,'3'),
(4,'4'),
(5,'1'),
(5,'2'),
(5,'3'),
(6,'1'),
(6,'2'),
(6,'3'),
(6,'4'),
(6,'5'),
(7,'1'),
(7,'2'),
(7,'3'),
(7,'4'),
(7,'5'),
(7,'6'),
(7,'7'),
(7,'8'),
(8,'1'),
(8,'2'),
(8,'3'),
(8,'4'),
(8,'5'),
(8,'6');


/*Ошибки в расписании. Если фильм заканчивается ровно тогда, когда начинается второй - это также считается ошибкой*/
select
    f1.name,
    sh1.start_at,
    f1.duration,
    f2.name,
    sh2.start_at,
    f2.duration
from
    shedule sh1
        join film f1
             on sh1.film_id = f1.id
        join shedule sh2
             on sh2.start_at between sh1.start_at and dateadd(minute, f1.duration, sh1.start_at)
                 and sh1.id <> sh2.id
        join film f2
             on sh2.film_id = f2.id
order by sh1.start_at


/*Перерывы между фильмами*/
select
    t.name,
    t.start_at,
    t.duration,
    t.next_at,
    DATEDIFF(minute, end_at, next_at) as dif
from
    (select
         f1.id,
         f1.name,
         f1.duration,
         sh1.start_at,
         dateadd(minute, f1.duration, sh1.start_at) as end_at,
         (select min(sh2.start_at)
          from shedule sh2
          where sh2.start_at > sh1.start_at) next_at
     from
         shedule sh1
             join film f1
                  on sh1.film_id = f1.id) t
where DATEDIFF(minute, end_at, next_at) > 30
order by dif


/*Общего числа посетителей за все время, среднего числа зрителей за сеанс и общей суммы сборов по каждому фильму (отсортировать по убыванию прибыли)*/
select
    name,
    total_viewers,
    total_shows,
    avg_viewer_show,
    total_amount
from
    (select
         film.name,
         count(*) as total_viewers,
         count (distinct shedule_id) as total_shows,
         count(*) / count (distinct shedule_id) as avg_viewer_show,
         sum(price) as total_amount
     from
         film
             join shedule
                  on film.id = shedule.film_id
             join price
                  on price.shedule_id = shedule.id
             join ticket
                  on ticket.price_id = price.id
     group by
         film.name) t
order by total_amount desc
    compute
         sum(total_viewers),
         sum(total_shows),
         avg(avg_viewer_show),
         sum(total_amount)
;

/*число посетителей и кассовые сборы, сгруппированные по времени начала фильма: с 9 до 15, с 15 до 18, с 18 до 21, с 21 до 00:00*/
select
    film.name,
    sum(case when convert(time, shedule.start_at) >= '09:00:00' and convert(time, shedule.start_at) < '15:00:00' then 1 else 0 end) as viewers_09_15,
    sum(case when convert(time, shedule.start_at) >= '09:00:00' and convert(time, shedule.start_at) < '15:00:00' then price.price else 0 end) as amounts_09_15,
    sum(case when convert(time, shedule.start_at) >= '15:00:00' and convert(time, shedule.start_at) < '18:00:00' then 1 else 0 end) as viewers_15_18,
    sum(case when convert(time, shedule.start_at) >= '15:00:00' and convert(time, shedule.start_at) < '18:00:00' then price.price else 0 end) as amounts_15_18,
    sum(case when convert(time, shedule.start_at) >= '18:00:00' and convert(time, shedule.start_at) < '21:00:00' then 1 else 0 end) as viewers_18_21,
    sum(case when convert(time, shedule.start_at) >= '18:00:00' and convert(time, shedule.start_at) < '21:00:00' then price.price else 0 end) as amounts_18_21,
    sum(case when convert(time, shedule.start_at) >= '21:00:00' then 1 else 0 end) as viewers_21_00,
    sum(case when convert(time, shedule.start_at) >= '21:00:00' then price.price else 0 end) as amounts_21_00
from
    film
        join shedule
             on film.id = shedule.film_id
        join price
             on shedule.id = price.shedule_id
        join ticket
             on price.id = ticket.price_id
group by film.name
