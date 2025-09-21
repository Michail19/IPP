import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { AppComponent } from './app';
import { ContactDetailsComponent } from './contactlist/contact-details/contact-details';
import { ContactListComponent } from './contactlist/contact-list/contact-list';
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
