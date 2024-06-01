import React, { useState, useEffect } from 'react';
import { Chart as ChartJS, ArcElement, Tooltip, Legend, Chart } from 'chart.js';
import { Doughnut } from 'react-chartjs-2';
import Cookies from 'js-cookie';
ChartJS.register(ArcElement, Tooltip, Legend);
const fetchData = async (url) => {
  try {
    const jwtToken = Cookies.get('jwtToken');
    // Получаем userId из куки
    const userId = Cookies.get('userId');
    if (jwtToken && userId) {
      // Вы можете использовать jwtToken и userId здесь
      console.log('jwtToken:', jwtToken);
      console.log('userId:', userId);
    } else {
      // Обработка ситуации, когда куки не содержат необходимых данных
      console.log('Данные не найдены в куки');
    }
    
    const response = await fetch(url, {
      headers: {
        'Authorization': jwtToken,
        'UserId': userId
      }
    });
    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Error fetching data:', error);
    return null;
  }
};
const options = {
  layout: {
    padding: 5, // пустота между частями диаграммы (в пикселях)
    cutout: '75%', // ширина диаграммы
  },
  plugins: {
    legend: {
      position: 'right',
      labels: {
        font: {
          family: 'Montserrat', // задаем шрифт Montserrat для легенды
          color: 'black'
        },
      },
    },
    tooltip: {
      bodyFontFamily: 'Montserrat', // задаем шрифт Montserrat для текста всплывающей подсказки
    },
  },
};
const CyclicDiagram = ({url}) => {
  const userInfo = JSON.parse(localStorage.getItem('userInfo'));

  const [data, setData] = useState([]);

  useEffect(() => {
    const fetchDataFromServer = async () => {
      const newData = await fetchData(url);
      console.log(newData);
      setData(newData);
    };
    fetchDataFromServer();
  }, []);
  // Если данные не загружены, показываем заглушку или сообщение о загрузке
  if (data.length === 0) {
    return <div>Loading...</div>;
  }
  const labels = [];
  const datas = [];

  // Преобразование данных из JSON в формат, необходимый для построения диаграммы
  data.forEach(item => {
      const key = Object.keys(item)[0];
      labels.push(key);
      datas.push(item[key]);
  });

  const chartData = {
    labels: labels,
    datasets: [{
      label: "count",
      data: datas,
      options: options,
      backgroundColor: [
        'rgba(255, 170, 117)',
        'rgba(31, 92, 182, 0.9)',
        'rgba(217, 217, 217)',
        'rgba(242, 87, 87, 0.9)',
      ],
    }]
  };

  return (
    <div>
      <Doughnut data={chartData} options={options} />
    </div>
  );
};

export default CyclicDiagram;