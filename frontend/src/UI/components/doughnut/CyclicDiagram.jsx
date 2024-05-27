import React, { useState, useEffect } from 'react';
import { Chart as ChartJS, ArcElement, Tooltip, Legend, Chart } from 'chart.js';
import { Doughnut } from 'react-chartjs-2';

ChartJS.register(ArcElement, Tooltip, Legend);
const fetchData = async () => {
  try {
    const response = await fetch(`http://localhost:8082/categories/getBestCategories`); // Заменить на реальный URL сервера
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
const CyclicDiagram = () => {
  const userInfo = JSON.parse(localStorage.getItem('userInfo'));

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
      label: "count",
      data: data.map(data => data.id),
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