import React, { useState, useEffect } from "react";
import { Bar } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend } from 'chart.js';
import Cookies from 'js-cookie';

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const userInfo = JSON.parse(localStorage.getItem('userInfo'));
const userId = userInfo ? userInfo.map(userInfo => userInfo.id) : null;

const fetchData = async () => {
  try {
    const userId = Cookies.get('userId');
    const jwtToken = Cookies.get('jwtToken');
    const headers = {
      'Content-Type': 'application/json',
      'Authorization': jwtToken,
      'UserId': userId
    };

    const options = {
      method: 'GET',
      headers: headers
    };


    const responseExpenses = await fetch('http://localhost:8082/expenses/expensesByMonths', options); // Заменить на реальный URL сервера
    const responseIncomes = await fetch('http://localhost:8082/incomes/incomesByMonths', options);
    const dataExp = await responseExpenses.json();
    const dataInc = await responseIncomes.json();
    console.log("AAAAAAAAAAAAAAA");
    console.log(dataExp);
    console.log(dataInc);
    return [dataExp, dataInc];
  } catch (error) {
    console.error('Error fetching data:', error);
    return null;
  }
};

const options = {
  responsive: true,
  plugins: {
    legend: {
      position: 'bottom',
    },
    color: 'black',
    font: {
      weight: 'bold',
      family: 'Montserrat',
    },
  },
};

const Gistogram = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    const fetchDataFromServer = async () => {
      const newData = await fetchData();
      console.log(newData);
      setData(newData);
    };
    fetchDataFromServer();
  }, []);

  console.log("data is ");
  console.log(data);

  const labels = [12,  1, 2, 3,4,5];
  const expenses = [0, 0, 0, 0, 0, 0];
  const incomes = [0, 0, 0, 0, 0, 0];

  const keys = new Set();

  for (let i = 0; i < data.length; i++) {
      for (var key2 in data[i]){
        keys.add(key2);
      }
  }

  const arrKeys = Array.from(keys);
  console.log("arr");
  console.log(arrKeys);



  for (var key in arrKeys) {

    console.log(key);
    console.log(data[0][key]); //data[0] for expense [1] for income
    for (var key2 in data[0][key]){
      //labels.add(key2);
      //console.log("data key2 is ");
      //console.log(key2);s
      //console.log(data[0][key][key2]);
      //var idx = labels.indexOf(key2)+1;
      //console.log("idx");
      //console.log(idx);
      expenses[key2] = data[0][key][key2];
      //expenses[key2] = data[0][key][key2]; //expense
    }

    for (var key2 in data[1][key]){
      //labels.add(key2);
      //console.log("data key2 is ");
      //console.log(key2);
      //console.log(data[1][key][key2]);
      //var idx = labels.indexOf(key2)+1;
      //console.log("idx");
      //console.log(idx);
      incomes[key2] = data[1][key][key2];
      //incomes[key2] = data[1][key][key2]; //income
      
    }

  }
  console.log(labels);
  console.log(expenses);
  console.log(incomes);


  if (data.length === 0) {
    return <div>Loading...</div>;
  }
  const labels_arr = Array.from(labels);
  const chartData = {
    labels: labels_arr,
    datasets: [
      {
        label: "Доходы",
        data: incomes,
        options: options,
        backgroundColor: 'rgba(31, 92, 182, 0.9)'
      },
      {
        label: "Расходы",
        data: expenses,
        options: options,
        backgroundColor: 'rgba(242, 87, 87, 0.9)',
      }
    ]
  };

  return <Bar options={options} data={chartData} />;
};

export default Gistogram;