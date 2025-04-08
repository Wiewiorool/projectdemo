create table Type_of_car
(
type_of_car_id serial not null primary key,
value decimal(8,2) not null
);

create table Car_details
(
car_details_id serial not null primary key,
model_name varchar(10) not null,
color varchar(10) not null,
production_year date not null,
horse_power varchar(10) not null,
number_of_doors integer not null,
FOREIGN KEY (type_of_car_id) references Type_of_car(type_of_car_id) on delete cascade
);

create table Car
(
car_id serial not null primary key,
FOREIGN KEY (car_details_id) references Car_details(car_details_id) on delete cascade,
buy_car_price decimal(8,2)not null
);

create table Personal_data
(Personal_data_id serial not null primary key,
name varchar(10) not null,
surname varchar(10) not null
);

create table Client
(client_id serial not null primary key,
FOREIGN KEY (personal_data_id) references Personal_data(personal_data_id) on delete cascade,
owned_cars integer not null,
FOREIGN KEY (car_id) references Car(car_id) on delete cascade
);

create table Dealer
(dealer_id serial not null primary key,
degree varchar(10) not null,
FOREIGN KEY (personal_data_id) references Personal_data(personal_data_id) on delete cascade
);

create table Sale
(sale_id serial not null primary key,
FOREIGN KEY (dealer_id) references Dealer(dealer_id) on delete cascade,
FOREIGN KEY (client_id) references Client(client_id) on delete cascade,
FOREIGN KEY (car_id) references Car(car_id) on delete cascade ,
date timestamp not null,
sell_car_price decimal(8,2) not null
);