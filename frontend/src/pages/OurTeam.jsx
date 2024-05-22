import React, { useContext, useState, Suspense, startTranslation } from "react";

import style from "../styles/Light/Login.module.css"
import 'bootstrap/dist/css/bootstrap.min.css';
import { useTranslation } from 'react-i18next';
import { useHistory } from 'react-router-dom';

const OurTeam = () => {
    // для смены языка с русского на английский и тд
    const { t, i18n } = useTranslation();
    // для перехода между страницами
    const history = useHistory();
    const returnToLogin = () => {
        history.push('/login');
    };
    return (
        <div>
            <Suspense fallback={<div>Loading...</div>}>
                {startTranslation}
            </Suspense>
            {/** контейнер страницы */}
            < div className={style.LoginPage} >
                {/** заголовок*/}
                < div className={style.Header} >
                    <a
                        onClick={returnToLogin}
                        className={style.HeaderLink}
                    >
                        WealthFamily
                    </a>
                </div >
                <div
                    style={{ display: "flex", justifyContent: "center", alignItems: "center" }}
                >
                    <a
                        className={style.git}
                    >
                        {t('github-project')}
                    </a>
                </div>
                {/** ссылки внизу страницы */}
                <div className={style.Bottom}>
                    <h4>WealthFamily © 2024</h4>
                    <a
                        className={style.text}
                        style={{
                            height: "100%",
                            width: "100%"
                        }}
                    >
                        {t('our-team')
                        }
                    </a>
                    <a
                        onClick={() => i18n.changeLanguage('en')}
                        className={style.text}
                    >
                        English
                    </a>
                    <a
                        onClick={() => i18n.changeLanguage('ru')}
                        className={style.text}
                    >
                        Русский
                    </a>
                </div>
            </div >
        </div>
    );
};
export default OurTeam;