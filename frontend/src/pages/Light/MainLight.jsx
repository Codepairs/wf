import React, { useContext, useState, Suspense, startTranslation } from "react";
import { AuthContext } from "../../context";
import style from "../../styles/Light/Main.module.css"
import MyButton from "../../UI/components/button/MyButton";
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import { useTranslation } from 'react-i18next';
import { useHistory } from 'react-router-dom';
import Gistogram from "../../UI/components/gistogram/Gistogram.jsx";
import CyclicDiagram from "../../UI/components/doughnut/CyclicDiagram.jsx";
import MyTable from "../../UI/components/table/Table.jsx";
import { FaBell } from 'react-icons/fa';
import { FaCog } from 'react-icons/fa';

const MainLight = () => {
    const [period, setPeriod] = useState(null);
    const handleYearButtonClick = (year) => {
        setPeriod(year);
        // Здесь можно добавить логику для отправки переменной period на сервис и обновления данных компонента
    };

    const userInfo = JSON.parse(localStorage.getItem('userInfo'));
    // для смены языка с русского на английский и тд
    const { t, i18n } = useTranslation();
    // для перехода между страницами
    const history = useHistory();
    // при нажатии на кнопку регистрации
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
                    <div>
                        <FaBell 
                        style={{marginTop: "13px",marginRight: "10px", marginLeft: "120px"}}
                        />
                        <FaCog
                        style={{marginTop: "13px", marginRight: "20px" }}
                        />
                        {/* Отображение модального окна при isModalOpen === true */}
                    </div>
                    <div className={style.Settings}>
                        <MyButton
                            style={{ backgroundColor: '#1F5CB6', color: '#ffffff', width: "130px", height: "90%", marginRight: "5%", borderRadius: "11px", fontSize: "12px", marginTop: "0.5%" }}
                            onClick={returnLinks}
                        >
                            {t('account')}
                        </MyButton>
                    </div>
                </div>
                <div className={style.Diagrams}>
                    <div className={style.First}>
                        <div className={style.Gistograms}>
                            <nav class="navbar navbar-light bg-light"
                                style={{ display: "flex", alignItems: "center", height: "50px" }}>
                                <p style={{ fontSize: "14px", fontFamily: "Montserrat", fontWeight: "bold", height: "20%", marginLeft: "2%", color: "#545454" }}> Доходы и расходы
                                </p>
                                <MyButton
                                    style={{ backgroundColor: '#ffffff', color: '#000000', width: "5%", borderRadius: "7px", marginRight: "5%", height: "20%", fontSize: "12px" }}
                                    onClick={() => handleYearButtonClick(2024)}
                                >
                                    2024
                                </MyButton>
                                <MyButton
                                    style={{ backgroundColor: '#ffffff', color: '#000000', width: "5%", borderRadius: "7px", marginRight: "5%", height: "20%", fontSize: "12px" }}
                                    onClick={() => handleYearButtonClick(2023)}
                                >
                                    2023
                                </MyButton>
                                <MyButton
                                    style={{ backgroundColor: '#ffffff', color: '#000000', width: "5%", borderRadius: "7px", marginRight: "5%", height: "20%", fontSize: "12px" }}
                                    onClick={() => handleYearButtonClick(2022)}
                                >
                                    2022
                                </MyButton>
                            </nav>
                            <Gistogram />
                        </div>
                        <div style={{ width: "50%" }}>
                            <nav class="navbar navbar-light bg-light"
                                style={{ display: "flex", alignItems: "center", height: "50px" }}>
                                <p style={{ fontSize: "14px", fontFamily: "Montserrat", fontWeight: "bold", height: "20%", marginLeft: "2%", color: "#545454" }}> Доходы и расходы
                                </p>
                                <MyButton
                                    style={{ backgroundColor: '#ffffff', color: '#000000', width: "5%", borderRadius: "7px", marginRight: "5%", height: "20%", fontSize: "12px" }}
                                    onClick={returnLinks}
                                >
                                    2024
                                </MyButton>
                            </nav>
                            <div style={{ display: "flex", flexDirection: "row" }}>
                            <div className={style.FirstDought}>
                                    <CyclicDiagram url="http://localhost:8082/categories/getBestCategoriesExpenses" labels={['Зарплата', 'Премия', 'Подарки', 'Акции']} />
                                </div>
                                <div className={style.SecondDought}>
                                    <CyclicDiagram  url="http://localhost:8082/categories/getBestCategoriesIncomes" labels={['Еда', 'Транспорт', 'Лекарства', 'Остальное']} />
                                </div>
                            </div>

                        </div>
                    </div>
                    <div className={style.Tables}>
                        <MyTable />
                    </div>
                </div>
            </div>
        </div>
    );
};
export default MainLight;