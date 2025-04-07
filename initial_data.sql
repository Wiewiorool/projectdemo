create table Type_of_car
(
type_of_car_id serial not null primary key,
value decimal(8,2) not null
)

create table Car_details
(
car_details_id serial not null primary key,
model_name char(10) not null,
color char(10) not null,
production_year date not null,
horse_power char(10) not null,
number_of_doors integer not null,
type_of_car_id serial not null references Type_of_car(type_of_car_id)
)

create table Car
(
car_id char(20) not null primary key,
car_details_id char(20)not null references Car_details(car_details_id),
buy_car_price decimal(8,2)not null
)

create table Personal_data
(Personal_data_id serial not null primary key,
name char(10) not null,
surname char(10) not null
)

create table Client
(client_id serial not null primary key,
personal_data_id serial not null references Personal_data(personal_data_id),
owned_cars integer not null,
car_id serial not null references Car(car_id)
)

create table Dealer
(dealer_id serial not null primary key,
degree char(10) not null,
personal_data_id serial not null references Personal_data(personal_data_id)
)

create table Sale
(sale_id serial not null primary key,
dealer_id serial not null references Dealer(dealer_id),
client_id serial not null references Client(client_id),
car_id serial not null references Car(car_id),
date timestamp not null,
sell_car_price decimal(8,2) not null
)