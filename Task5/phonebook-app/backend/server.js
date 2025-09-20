var express = require("express");
var bodyParser = require("body-parser");
var mongodb = require("mongodb");
var ObjectID = mongodb.ObjectID;
var CONTACTS_COLLECTION = "contacts";
var app = express();
app.use(bodyParser.json());
// Создайте переменную базы данных вне обратного вызова соединения
с базой данных, чтобы повторно использовать пул соединений в вашем
приложении.
var db;
// Подключитесь к базе данных перед запуском сервера приложений.
mongodb.MongoClient.connect(process.env.MONGODB_URI || "mongodb://localhost:27017/test", function (err, client) {
if (err) {
console.log(err);
process.exit(1);
}
// Сохраните объект базы данных из обратного вызова для повторного
использования.
db = client.db();
console.log("База данных подключена");
// Инициализация приложения
var server = app.listen(process.env.PORT || 8080, function () {
var port = server.address().port;
console.log("Приложение запущенно на порту", port);
});
});
// Маршруты API
