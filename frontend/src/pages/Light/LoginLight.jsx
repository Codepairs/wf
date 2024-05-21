import React, { useContext, useState, Suspense, startTranslation } from "react";
import { AuthContext } from "../../context";
import style from "../../styles/Light/Login.module.css";
import MyButton from "../../UI/components/button/MyButton";
import MyInput from "../../UI/components/input/MyInput";
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import mockup from '../../mockup.svg';
import qr from '../../qr.svg';
import { useTranslation } from 'react-i18next';
import { useHistory } from 'react-router-dom';

const LoginLight = () => {
    // состояния для авторизации (об это я подумаю позже)
    const { isAuth, setIsAuth } = useContext(AuthContext);
    // переменные 
    const [loginData, setLoginData] = useState({ username: '', password: '' });
    // не помню зачем делала, но пусть пока будут
    const [submitted, setSubmitted] = useState(false);
    // для чтения логина из поля ввода
    const handleUsernameChange = (event) => {
        const username = event.target.value;
        setLoginData({ ...loginData, username });
    };
    // для чтения пароля из поля ввода
    const handlePasswordChange = (event) => {
        const password = event.target.value;
        setLoginData({ ...loginData, password });
    };
    const handleRedirectToMain = () => {
        history.push('/main');
    };
    const [isRemember, setRemember] = useState()
    // для отправления запросов
    const login = async (event) => {
        event.preventDefault();
        setSubmitted(true);

        const { username, password } = loginData;
        console.log(loginData)

        // Проверка значений только если они были введены
        if (username && password) {
            try {
                const response = await axios.post('https://postman-echo.com/post', {
                    username,
                    password
                });

                console.log(response.data);
                if (response.data && response.data.success) {
                    console.log(response.data);
                    console.log(response.data.success);

                } else {
                    alert('Неправильный логин или пароль');
                }
            } catch (error) {
                alert('Произошла ошибка при входе. Пожалуйста, попробуйте позже.');
            }
        }
    };
    // для смены языка с русского на английский и тд
    const { t, i18n } = useTranslation();
    // для перехода между страницами
    const history = useHistory();
    // при нажатии на кнопку регистрации
    const handleRedirectToRegistration = () => {
        history.push('/registration');
    };
    // при нажатии на надпись Забыли пароль?
    const handleRedirectToChangeCode = () => {
        history.push('/change-code');
    };
    const returnToLogin = () => {
        history.push('/login');
    };
    const returnOurTeam = () => {
        history.push('/ourteam');
    };
    const returnLinks = () => {
        history.push('/links');
    };
    // проверка корректны ли данные, которые введены в поля логина и пароля
    const [showForgotPassword, setShowForgotPassword] = useState(false);
    const handleLogin = () => {
        const { username, password } = loginData;
        console.log(username, password);
        if (username === 'TestUser' || password === '123') {
            handleRedirectToMain();
        } else {
            // когда-нибудь тут будет проверка :)
            if (false) {
                setShowForgotPassword(false); // а пока что я няшка <3
            } else {
                // Некорректный логин или пароль
                setShowForgotPassword(true);
            }
        }
    };
    return (
        <div>
            <Suspense fallback={<div>Loading...</div>}>
                {startTranslation}
            </Suspense>
            {/** контейнер страницы */}
            <div className={style.LoginPage}>
                {/** заголовок*/}
                <div className={style.Header}>
                    <a
                        onClick={returnToLogin}
                        className={style.HeaderLink}
                    >
                        WealthFamily
                    </a>
                </div>
                {/** основные компоненты */}
                <div className="container-fluid">
                    <div className={style.MainPage}>
                        <div className="row">
                            {/** пустота*/}
                            <div className={'col-lg-2'}></div>
                            {/** основная информация и ссылки для скачивания */}
                            <div className={`col-lg-5 text-center`}>
                                <div className={style.Info}>
                                    <h2>{t('welcome')}</h2>
                                    <h3>{t('else')}</h3>
                                    <img src={mockup} />
                                    <div className={style.Link}>
                                        <a
                                            onClick={returnLinks}
                                            className={style.text}
                                        >
                                            App Store
                                        </a>
                                        <a
                                            onClick={returnLinks}
                                            className={style.text}
                                        >
                                            Google Play
                                        </a>
                                        <a
                                            onClick={returnLinks}
                                            className={style.text}
                                        >
                                            Linux
                                        </a>
                                        <a
                                            onClick={returnLinks}
                                            className={style.text}
                                        >
                                            Windows
                                        </a>
                                    </div>
                                </div>
                            </div>
                            {/** форма*/}
                            <div className={`col-lg-3`}>
                                <div className={style.MainForm}>
                                    <form className={style.form} onSubmit={login} >
                                        <h1>{t('enter')}</h1>
                                        <MyInput
                                            className={style.inputLogin}
                                            type="text"
                                            placeholder={t('placeholder-login')}
                                            onChange={handleUsernameChange}
                                        />
                                        <MyInput
                                            className={style.inputPass}
                                            type="password"
                                            placeholder={t('placeholder-pass')}
                                            onChange={handlePasswordChange}
                                        />
                                        { // условное отображение, чтобы можно было перейти на страницу смены пароля
                                            showForgotPassword
                                                ?
                                                <a
                                                    onClick={handleRedirectToChangeCode}
                                                    className={style.error}
                                                >
                                                    {t('forgot-password')}
                                                </a>
                                                :
                                                <div className={style.Remember}>
                                                    <p className={style.remember}>
                                                        <input
                                                            onChange={(e) => setRemember(e.target.checked)}
                                                            type="checkbox"
                                                            checked name="remember"
                                                        />
                                                        {t('remember-me')}
                                                    </p>
                                                </div>}
                                        <MyButton
                                            onClick={handleLogin}
                                            type="submit"
                                            style={{ backgroundColor: '#1F5CB6', color: '#ffffff'}}
                                        >
                                            {t('button-login')}
                                        </MyButton>
                                        <MyButton
                                            onClick={handleRedirectToRegistration}
                                            style={{ backgroundColor: '#ffffff', color: '#1F5CB6', margin: '0px' }}
                                        >
                                            {t('register')}
                                        </MyButton>
                                    </form>
                                </div>
                                {/** QR */}
                                <div className={style.QR} >
                                    <img src={qr} className={style.qrImg} />
                                    <div className={style.textQr}>
                                        <h5 className={style.text}>{t('qr')}</h5>
                                        <h6 className={style.text}>{t('let-scan')}</h6>
                                        <a className={style.more}>{t('more')}</a>
                                    </div>
                                </div>
                            </div>
                            {/** ссылки внизу страницы */}
                            <div className={style.Bottom}>
                                <h4>WealthFamily © 2024</h4>
                                <a
                                    className={style.text}
                                    onClick={returnOurTeam}
                                    style={{marginRight: "15%"}}
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
                                    style={{marginRight: "10%"}}
                                >
                                    Русский
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};
export default LoginLight;
