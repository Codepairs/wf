import React, { useContext, useState, Suspense, startTranslation } from "react";
import { AuthContext } from "../../context";
import style from "../../styles/Light/Login.module.css"
import MyButton from "../../UI/components/button/MyButton";
import MyInput from "../../UI/components/input/MyInput";
import 'bootstrap/dist/css/bootstrap.min.css';
import Gistogram from "../../UI/components/gistogram/Gistogram.tsx";
import CyclicDiagram from "../..//UI/components/doughnut/CyclicDiagram.tsx";
import Table from "../../UI/components/table/Table.tsx"
import { useTranslation } from 'react-i18next';
import { useHistory } from 'react-router-dom';
const MainLight = () => {
    const { isAuth, setIsAuth } = useContext(AuthContext);
    // для смены языка с русского на английский и тд
    const { t, i18n } = useTranslation();
    // для перехода между страницами
    const history = useHistory();
    // при нажатии на кнопку регистрации

    // при нажатии на надпись Забыли пароль?
    const returnToLogin = () => {
        history.push('/login');
    };
    const returnLinks = () => {
        history.push('/links');
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
                    <div className={style.Buttons}>
                        <MyButton
                            style={{ backgroundColor: '#1F5CB6', color: '#ffffff', width: "15%", height: "90%", marginRight: "5%", borderRadius: "11px", fontSize: "12px", marginTop: "0.5%" }}
                            onClick={returnLinks}
                        >
                            {t('report')}
                        </MyButton>
                        <MyButton
                            style={{ backgroundColor: '#ffffff', color: '#000000', width: "15%", borderRadius: "11px", height: "90%", marginRight: "5%", fontSize: "12px", marginTop: "0.5%" }}
                            onClick={returnLinks}
                        >
                            {t('plan')}
                        </MyButton>
                    </div>
                </div>
                <div className={style.Diagrams}>
                    <div className={style.First}>
                        <div className={style.Gistograms}>
                            <Gistogram />
                        </div>
                        <div className={style.FirstDought}>
                            <CyclicDiagram />
                        </div>
                        <div className={style.SecondDought}>
                            <CyclicDiagram />
                        </div>
                    </div>
                    <div className={style.Tables}>
                        <Table />
                    </div>
                </div>
            </div>
        </div>
    );
};
export default MainLight;
