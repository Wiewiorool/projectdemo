create table Type_of_car
(
type_of_car_id serial not null primary key,
type_of_car_value varchar(50) not null
);

create table Car_details
(
car_details_id serial not null primary key,
model_name varchar(10) not null,
color varchar(10) not null,
production_year integer not null,
horse_power integer not null,
number_of_doors integer not null,
type_of_car_id integer not null,
FOREIGN KEY (type_of_car_id) references Type_of_car(type_of_car_id) on delete cascade
);

create table Car
(
car_id serial not null primary key,
car_details_id integer not null,
buy_car_price decimal(8,2)not null,
FOREIGN KEY (car_details_id) references Car_details(car_details_id) on delete cascade
);

create table Personal_data
(Personal_data_id serial not null primary key,
name varchar(10) not null,
surname varchar(10) not null
);

create table Client
(client_id serial not null primary key,
personal_data_id integer not null,
owned_cars integer not null,
car_id integer not null,
FOREIGN KEY (personal_data_id) references Personal_data(personal_data_id) on delete cascade,
FOREIGN KEY (car_id) references Car(car_id) on delete cascade
);

create table Dealer
(dealer_id serial not null primary key,
degree varchar(10) not null,
personal_data_id integer not null,
FOREIGN KEY (personal_data_id) references Personal_data(personal_data_id) on delete cascade
);

create table Sale
(sale_id serial not null primary key,
dealer_id integer not null,
client_id integer not null,
car_id integer not null,
sale_date timestamp not null,
sell_car_price decimal(8,2) not null,
FOREIGN KEY (dealer_id) references Dealer(dealer_id) on delete cascade,
FOREIGN KEY (client_id) references Client(client_id) on delete cascade,
FOREIGN KEY (car_id) references Car(car_id) on delete cascade
);
