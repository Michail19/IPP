import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { ContactListComponent } from './contactlist/contact-list/contact-list';
import { ContactDetailsComponent } from './contactlist/contact-details/contact-details';
@NgModule({
    declarations: [
        AppComponent,
        ContactDetailsComponent,
        ContactListComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
      HttpClientModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule { }
