import React, { useMemo, useState, useEffect } from 'react';
import { useTable } from 'react-table';
import { COLUMNS } from "../constants/Columns.jsx";
import './table.css';
import MyButton from '../button/MyButton.jsx';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import Cookies from 'js-cookie';
import dateFormat, { masks } from "dateformat";


function Table() {
  const userInfo = JSON.parse(localStorage.getItem('userInfo'))
  const columns = useMemo(() => COLUMNS, []);
  const [data, setData] = useState([]);
  const [dataExp, setDataExp] = useState([]);
  const fetchDataIncomes = async () => {
    try {
      const userId = Cookies.get('userId');
      const jwtToken = Cookies.get('jwtToken');
      const headers = {
        'Content-Type': 'application/json',
        'Authorization': jwtToken,
        'UserId': userId
      };

      const options = {
        method: 'POST',
        headers: headers
      };
      const response = await fetch(`http://localhost:8082/users/incomesInLastThreeDays`, options); // Заменить на реальный URL сервера
      const data = await response.json();
      console.log("data for table");
      console.log(data);
      return data;
    } catch (error) {
      console.error('Error fetching data:', error);
      return null;
    }
  };
  const fetchDataExpenses = async () => {
    try {
      const userId = Cookies.get('userId');
      const jwtToken = Cookies.get('jwtToken');
      const headers = {
        'Content-Type': 'application/json',
        'Authorization': jwtToken,
        'UserId': userId
      };

      const options = {
        method: 'POST',
        headers: headers
      };
      const response = await fetch(`http://localhost:8082/users/expensesInLastThreeDays`, options); // Заменить на реальный URL сервера
      const data = await response.json();
      console.log("data for table");
      console.log(data);
      return data;
    } catch (error) {
      console.error('Error fetching data:', error);
      return null;
    }
  };

  useEffect(() => {
    const fetchDataFromServer = async () => {
      const newData = await fetchDataIncomes();
      const newestData = await fetchDataExpenses();
      const combinedData = newData.concat(newestData);
      console.log(newData);
      setData(combinedData);
      setDataExp(newestData);
    };
    fetchDataFromServer();
  }, []);

  const tableInstance = useTable({
    columns,
    data
  });
  const { getTableProps, getTableBodyProps, headerGroups, footerGroups, rows, prepareRow } = tableInstance;

  const handleDeleteRow = async (rowIndex) => {
    const deletedRow = data[rowIndex];
    const updatedData = data.filter((_, index) => index !== rowIndex);
    setData(updatedData);

    try {
      const response = await fetch('https://eolzekfev7x8pe2.m.pipedream.net', {
        method: 'DELETE',
        body: JSON.stringify(deletedRow),
        headers: {
          'Content-Type': 'application/json'
        }
      });
      if (response.ok) {
        console.log('Данные успешно удалены на сервере');
      } else {
        console.error('Ошибка при удалении данных на сервере');
      }
    } catch (error) {
      console.error('Произошла ошибка при отправке запроса на сервер:', error);
    }
  };

  // Отправляем данные на сервер
  // Отправляем данные на сервер
  const addDataIncomesToServer = async (data) => {
    const jwtToken = Cookies.get('jwtToken');
    const userId = Cookies.get('userId');
    const newData = data.map(item => ({ ...item, userId }));
    const comment = newData[0].comment ? newData[0].comment : "";
    const value = newData[0].value ? newData[0].value.toString() : "";
    const userId1 = newData[0].userId ? newData[0].userId : "";
    const date = newData[0].getDate;
    //var getDate = dateFormat(date, "dd.mm.yyyy HH:MM:ss");

    try {
      const response1 = await fetch(`http://localhost:8082/categories/categoryByName?name=${newData[0].categoryId}`, {
        method: 'GET',
        headers: {
          'Authorization': jwtToken,
          'UserId': userId
        }
      });
      const categoryIdByName = await response1.text();
      const jsonObject = JSON.parse(categoryIdByName);
      const nameFieldValue = jsonObject.id;
      const categoryId = nameFieldValue ? nameFieldValue : "";
      console.log('ID категории:', categoryId);

      const incomeCreateDto = {
        comment: comment,
        value: value,
        categoryId: categoryId, // Внесите правку здесь
        userId: userId1,
        getDate: date//.toString()//.toISOString()
      };
      const json = JSON.stringify(incomeCreateDto);
      console.log(json);
      try {
        const response = await fetch('http://localhost:8082/incomes', {
          method: 'POST',
          body: json,
          headers: {
            'Authorization': jwtToken,
            'UserId': userId,
            'Content-Type': 'application/json'
          }
        });
        const result = await response.json();
        console.log('Данные успешно отправлены на сервер:', result);

        // Обновляем данные в таблице
        //setData([{}, ...data]);
      } catch (error) {
        console.error('Произошла ошибка при отправке данных на сервер:', error);
      }
    } catch (error) {
      console.error('Произошла ошибка при получении ID категории:', error);
    }
  };
  // Отправляем данные на сервер
  const addDataExpensesToServer = async (data) => {
    const jwtToken = Cookies.get('jwtToken');
    const userId = Cookies.get('userId');
    const newData = data.map(item => ({ ...item, userId }));
    const comment = newData[0].comment ? newData[0].comment : "";
    const value = newData[0].value ? newData[0].value.toString() : "";
    const userId1 = newData[0].userId ? newData[0].userId : "";
    var getDate = newData[0].getDate;

    try {
      const response1 = await fetch(`http://localhost:8082/categories/categoryByName?name=${newData[0].categoryId}`, {
        method: 'GET',
        headers: {
          'Authorization': jwtToken,
          'UserId': userId
        }
      });
      const categoryIdByName = await response1.text();
      const jsonObject = JSON.parse(categoryIdByName);
      const nameFieldValue = jsonObject.id;
      const categoryId = nameFieldValue ? nameFieldValue : "";
      console.log('ID категории:', categoryId);

      const incomeCreateDto = {
        comment: comment,
        value: value,
        categoryId: categoryId, // Внесите правку здесь
        userId: userId1,
        getDate: getDate//.toString()//.toISOString()
      };
      const json = JSON.stringify(incomeCreateDto);
      console.log(json);
      try {
        const response = await fetch('http://localhost:8082/expenses', {
          method: 'POST',
          body: json,
          headers: {
            'Authorization': jwtToken,
            'UserId': userId,
            'Content-Type': 'application/json'
          }
        });
        const result = await response.json();
        console.log('Данные успешно отправлены на сервер:', result);

        // Обновляем данные в таблице
        //setData([{}, ...data]);
      } catch (error) {
        console.error('Произошла ошибка при отправке данных на сервер:', error);
      }
    } catch (error) {
      console.error('Произошла ошибка при получении ID категории:', error);
    }
  };
  const handleDateRangeConfirm = (selectedDates) => {
    setSelectedDateRange(selectedDates);
    // Perform data fetching and table update based on selected date range here
    setShowCalendar(false);
  };
  const handleAddExpense = () => {
    // Если кнопка была скрыта, показываем её обратно
    if (!showIncomeButton) {
      addDataExpensesToServer(data);

      setShowIncomeButton(true);
    } else {
      const newData = [{}, ...data]; // Добавляем пустую строку в начало данных таблицы
      setData(newData);
      // Скрываем кнопку "Добавить расход"
      setShowIncomeButton(false);
    }
  };

  const handleAddIncome = () => {
    // Если кнопка была скрыта, показываем её обратно
    if (!showExpenseButton) {
      addDataIncomesToServer(data);
      setShowExpenseButton(true);
    } else {
      const newData = [{}, ...data]; // Добавляем пустую строку в начало данных таблицы
      setData(newData);
      // Скрываем кнопку "Добавить доход"
      setShowExpenseButton(false);
    }
  };

  const [showIncomeButton, setShowIncomeButton] = useState(true);
  const [showExpenseButton, setShowExpenseButton] = useState(true);
  const handleCellChange = (value, row, column) => {
    const newData = data.map((d, index) => {
      if (index === row.index) {
        return {
          ...d,
          [column.id]: value
        };
      }
      return d;
    });
    setData(newData);
  };

  const [selectedDateRange, setSelectedDateRange] = useState();
  const [showCalendar, setShowCalendar] = useState(false);
  ;

  const handleDateRangeSelection = () => {
    setShowCalendar(true);
  };

  const handleCalendarClose = () => {
    setShowCalendar(false);
  };
  return (
    <div>
      <nav class="navbar navbar-light bg-light"
        style={{ display: "flex", alignItems: "center", height: "40px", position: "relative" }}>

        <MyButton
          style={{ backgroundColor: '#ffffff', color: '#000000', width: "60px", borderRadius: "7px", marginRight: "5px", marginLeft: "30px", height: "95%", fontSize: "12px", marginTop: "0.0%" }}

        >
          2024
        </MyButton>
        <MyButton
          style={{ backgroundColor: '#ffffff', color: '#000000', width: "5%", borderRadius: "7px", marginLeft: "0px", marginRight: "5px", height: "95%", fontSize: "12px", marginTop: "0.0%" }}

        >
          2023
        </MyButton>
        <MyButton
          style={{ backgroundColor: '#ffffff', color: '#000000', width: "5%", borderRadius: "7px", marginRight: "180px", height: "95%", fontSize: "12px", marginTop: "0.0%" }}

        >
          2022
        </MyButton>
        <MyButton
          style={{ backgroundColor: '#1F5CB6', color: '#ffffff', width: "20%", height: "95%", marginRight: "5%", borderRadius: "11px", fontSize: "12px", marginTop: "0.0%" }}
          onClick={handleDateRangeSelection}
        >
          {selectedDateRange ? selectedDateRange.toString() : '27 мая - 29 мая 2024'}
        </MyButton>

        <MyButton
          style={{ backgroundColor: '#1F5CB6', color: '#ffffff', width: "12%", height: "95%", marginRight: "3%", borderRadius: "11px", fontSize: "12px", marginTop: "0.0%", visibility: showExpenseButton ? 'visible' : 'hidden' }}
          onClick={handleAddExpense}
        >
          Добавить расход
        </MyButton>
        <MyButton
          style={{ backgroundColor: '#1F5CB6', color: '#ffffff', width: "12%", height: "95%", marginRight: "5%", borderRadius: "11px", fontSize: "12px", marginTop: "0.0%", visibility: showIncomeButton ? 'visible' : 'hidden' }}
          onClick={handleAddIncome}
        >
          Добавить доход
        </MyButton>
      </nav>
      {showCalendar && (
        <div className="calendar-popup">
          <DatePicker
            selected={selectedDateRange}
            onChange={handleDateRangeConfirm}
            startDate={selectedDateRange[0]}
            endDate={selectedDateRange[1]}
            selectsRange
            inline
          />
          <button onClick={handleCalendarClose}>Close</button>
        </div>
      )}
      {data.length > 0 && ( // Check if there is data before rendering the table
        <div style={{ overflowY: 'auto', maxHeight: '220px' }}>
          {console.log(data.length)}
          {console.log("AAA")}
          <table {...getTableProps()} className="table">
            <thead>
              {headerGroups.map((headerGroup) => (
                <tr {...headerGroup.getHeaderGroupProps()}>
                  {headerGroup.headers.map((column) => (
                    <th {...column.getHeaderProps()}>
                      {column.render('Header')}
                    </th>
                  ))}
                </tr>
              ))}
            </thead>
            <tbody {...getTableBodyProps()}>
              {rows.map((row, rowIndex) => {
                prepareRow(row);
                return (
                  <tr {...row.getRowProps()}>
                    {row.cells.map((cell, cellIndex) => {
                      return (
                        <td {...cell.getCellProps()}>
                          <input
                            type="text"
                            value={cell.value || ''}
                            onChange={(e) => handleCellChange(e.target.value, row, cell.column)}
                            className="input-field"
                          />
                        </td>
                      );
                    })}
                    <td>
                      <button onClick={() => handleDeleteRow(rowIndex)}>x</button>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

export default Table;