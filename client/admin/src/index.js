import React from "react";
import { render } from "react-dom";
import { Provider } from "react-redux";
import i18next from "i18next";
import { initReactI18next } from "react-i18next";
import store from "./store/store";
import App from "./App";

import "normalize.css";

import appTranslationRU from "./assets/locales/ru/app.json";
import commonTranslationRU from "./assets/locales/ru/common.json";
import accountTranslationRU from "./assets/locales/ru/account.json";

const resources = {
    ru: {
        app: appTranslationRU,
        common: commonTranslationRU,
        account: accountTranslationRU
    }
};

i18next
    .use(initReactI18next)
    .init({
        resources,
        lng: "ru"
    });

const Root = () => {
    return (
        <Provider store={ store }>
            <App />
        </Provider>
    )
};

render(<Root />, document.getElementById('root'));
