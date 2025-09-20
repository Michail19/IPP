import { Http, Response } from '@angular/http';
import { Injectable } from '@angular/core';
import { Contact } from './contact'; // импортируем ранее реализованный Contact
@Injectable()
export class ServiceAPI {
    private APIUrl = '/v1/contact'; // путь для сервиса
    constructor (private http: Http) {}
// создание нового контакта
    createContact(newContact: Contact): Promise<void | Contact> {
        return this.http.post(this.contactsUrl, newContact)
            .toPromise()
            .then(response => response.json() as Contact)
            .catch(this.handleError);
    }
// получение данных о контакте
    getContacts(): Promise<void | Contact[]> {
        return this.http.get(this.APIUrl)
            .toPromise()
            .then(response => response.json() as Contact[])
            .catch(this.handleError);
    }
// удаление контакта по id
    deleteContact(delContactId: String): Promise<void | String> {
        return this.http.delete(this.APIUrl + '/' + delContactId) // к текущему пути добавляется /id
            .toPromise()
            .then(response => response.json() as String)
            .catch(this.handleError);
    }
// обновление контакта по id
    updateContact(putContact: Contact): Promise<void | Contact> {
        var putUrl = this.APIUrl + '/' + putContact._id; // к текущему пути добавляется /id
        return this.http.put(putUrl, putContact)
            .toPromise()
            .then(response => response.json() as Contact)
            .catch(this.handleError);
    }
    private handleError (error: any) {
        let errMsg = (error.message) ? error.message :
            error.status ? `${error.status} – ${error.statusText}` : 'Ошибка сервера';
        console.error(errMsg); // Вывод сообщения в консоль браузера
    }
}
