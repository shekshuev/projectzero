import React, {useEffect, useState} from 'react';
import {View, Text} from 'react-native'
import MyInput from '../input/MyInput';
const Open = (props) => {
    //такое же состояние для ответа в виде написанного пользователем слова или слвсчтн
    const myInit = props.totalAns[props.number].variants
    const [ans, setAns] = useState(myInit)

    useEffect(() => {
        setAns(myInit)
    }, [props.number])

    useEffect(() => {
        let mass = props.totalAns
        mass[props.number].variants = ans
        props.setTotalAns(mass)
    }, [ans])

    return (
        <View style={{marginBottom:'60%'}}>
            <MyInput
                secure={false}
                onChange={setAns}
                label="Введите ответ сюда"
                value={ans}
            />
        </View>
    );
};

export default Open;
