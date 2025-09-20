import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { AppComponent } from './app.component';
import { ContactDetailsComponent } from './contacts/contact-details/contact-details.component';
import { ContactListComponent } from './contacts/contactlist/contact-list.component';
@NgModule({
    declarations: [
        AppComponent,
        ContactDetailsComponent,
        ContactListComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule { }

var express = require("express");
var bodyParser = require("body-parser");
var mongodb = require("mongodb");
var ObjectID = mongodb.ObjectID;
var CONTACTS_COLLECTION = "contacts";
var app = express();
app.use(bodyParser.json());
// Создание ссылки на каталог сборки Angular
var distDir = __dirname + "/dist/";
app.use(express.static(distDir));

