db = db.getSiblingDB('admin');
// move to the admin db - always created in Mongo
db.auth("test", "test");
// log as root admin if you decided to authenticate in your docker-compose file...
db = db.getSiblingDB('test');
// create and move to your new database
db.createUser({
    'user': "test",
    'pwd': "test",
    'roles': [{
        'role': 'dbOwner',
        'db': 'test'}]});
// user created