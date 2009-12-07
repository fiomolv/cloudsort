create user "CLOUDSORT" identified by "CLOUDSORT"
  default tablespace users
  temporary tablespace temp
  profile default;

grant unlimited tablespace to "CLOUDSORT";
grant connect to "CLOUDSORT";
grant resource to "CLOUDSORT";
alter user "CLOUDSORT" default role connect, resource;


