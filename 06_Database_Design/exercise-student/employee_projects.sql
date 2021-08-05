-- CREATE DATABASE employee_projects;

DROP TABLE IF EXISTS employee_project;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS project;

CREATE TABLE employee(
             employee_id serial primary key,
             department_id int not null,
             job_title varchar (100) not null,
             last_name varchar (70) not null,
             first_name varchar (100) not null,
             gender varchar (10) not null,
             gender_description varchar(30),
             date_of_birth varchar (32) not null,
             date_of_hire varchar (32) not null,
             
             constraint chk_employee_gender check (gender IN ('Male', 'femal', 'other')),
             constraint chk_gender_typedesc check ( (gender = 'Other' AND gender_description IS NOT NULL) 
                OR (gender != 'Other' AND gender_description IS NULL) )
      
);

CREATE TABLE department(
              department_id serial primary key,
            --employee_id int ,
              name varchar (100) not null
              
      --constraint fk_department_employee_id foreign key (employee_id) references employee(employee_id)

);
CREATE TABLE employee_project(
        employee_id int not null,
        project_id int not null,
        
        constraint fk_employee_project_employee_id foreign key (employee_id) references employee(employee_id),
        constraint fk_employee_project_project_id foreign key (project_id) references project(project_id),
        constraint pk_employee_project primary key (employee_id, project_id)

);


CREATE TABLE project(
             project_id serial primary key,
             employee_id int,
             start_date varchar (32),
             
             constraint fk_project_employee_id foreign key (employee_id) references employee(employee_id)
           
);
Start transaction;
rollback;
commit;


insert into employee (department_id, job_title , last_name,first_name, gender, date_of_birth, date_of_hire)
values  ('1','Mechanic', 'James', 'Robert', 'Male','11-25-1990', '12-11-2019');
   
insert into employee (department_id, job_title , last_name,first_name, gender, date_of_birth, date_of_hire)
values  ('1','Mechanic', 'Maria', 'Robert', 'Femal','11-20-1991', '12-10-2019');

insert into employee (department_id, job_title , last_name,first_name, gender, date_of_birth, date_of_hire)
values  ('3', 'Mechanic', 'John', 'Andrew', 'Male','10-20-1993', '02-10-2019');

insert into employee (department_id, job_title , last_name,first_name, gender, date_of_birth, date_of_hire)
values  ('2', 'Mechanic', 'Alex', 'Williams', 'Male','04-21-1993', '03-10-2019');
 
insert into employee (department_id, job_title , last_name,first_name, gender, date_of_birth, date_of_hire)
values  ('3','Mechanic', 'Kevin', 'jackson', 'Male','06-21-1994', '03-10-2019');  

insert into employee (department_id, job_title , last_name,first_name, gender, date_of_birth, date_of_hire)
values  ('1','Mechanic', 'Joycelin', 'Thomas', 'Femal','07-25-1989', '03-10-2019'); 

insert into employee (department_id, job_title , last_name,first_name, gender, date_of_birth, date_of_hire)
values  (2'','Mechanic', 'Joycelin', 'Thomas', 'Femal','07-25-1989', '03-10-2019');

insert into employee (department_id, job_title , last_name,first_name, gender, date_of_birth, date_of_hire)
values  (1'','Electrician', 'Pendro', 'Kilson', 'malel','08-11-1981', '04-02-2011');



 insert into employee_project values (1, 2);
 insert into employee_project values (1, 1);
 insert into employee_project values (2, 1);
 insert into employee_project values (3, 2);
 insert into employee_project values (3, 3);
 insert into employee_project values (4, 1);
 insert into employee_project values (4, 3);
 insert into employee_project values (5, 2);
 insert into employee_project values (6, 2);
 insert into employee_project values (6, 3);
 insert into employee_project values (7, 1);
 insert into employee_project values (7, 2);
 insert into employee_project values (8, 1);
 insert into employee_project values (8, 3);

select * from employee_project;

 

