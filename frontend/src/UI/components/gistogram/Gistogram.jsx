import React, { useState, useEffect } from "react";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import { Bar } from 'react-chartjs-2';
ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);
const userInfo = JSON.parse(localStorage.getItem('userInfo'))
const userId = userInfo ? userInfo.map(userInfo => userInfo.id) : null;
const response = await fetch(
  'https://jsonplaceholder.typicode.com/users/${userId}/data');
const fetchData = async () => {
  try {
    const response = await fetch(`https://jsonplaceholder.typicode.com/users?_limit=6`); // Заменить на реальный URL сервера
    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Error fetching data:', error);
    return null;
  }
};
export const options = {
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
  // Если данные не загружены, показываем заглушку или сообщение о загрузке
  if (data.length === 0) {
    return <div>Loading...</div>;
  }

  const chartData = {
    labels: data.map(data => data.name),
    datasets: [{
      label: "Доходы",
      data: data.map(data => data.id),
      options: options,
      backgroundColor: 'rgba(31, 92, 182, 0.9)'
    }, {
      label: "Расходы",
      data: data.map(data => data.id),
      options: options,
      backgroundColor: 'rgba(242, 87, 87, 0.9)',
    }
    ]
  };
  return <Bar options={options} data={chartData} />;
};
export default Gistogram;
