import React, {useState, useEffect} from 'react';
import {View, Text} from 'react-native';
import MyInput from './UI/input/MyInput';
import MyButton from './UI/button/MyButton';
import PostService from '../API/PostService';
import {useFetching} from '../hooks/useFetching';
import Geopos from '../API/Geopos';

const Login = (props) => {

    const [location, setLocation] = useState(null);

    useEffect(() => {
        //Geopos.getCoord(setLocation)
    }, []);

    const [login,setLogin] = useState("")
    const [password,setPassword] = useState("")
    return (
        <View>
            {console.log(location)}
            <MyInput value={login} onChange={setLogin} label="Логин" secure={false}/>
            <MyInput value={password} onChange={setPassword} label="Пароль" secure={true}/>
            <MyButton title ="Войти" onPress={()=>{
                props.action(login,password)
            }}/>
        </View>
    );
};

export default Login;
