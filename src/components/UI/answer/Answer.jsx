import React, {useEffect, useState} from 'react';
import {View, Text, StyleSheet, TouchableOpacity} from 'react-native'
const Answer = (props) => {
    const [touched, setTouched] = useState(props.defAns[props.number].variants[props.index])

    useEffect(()=>{
        setTouched(props.defAns[props.number].variants[props.index])
    }, [props.number])

    return (
            touched
            ?
            <TouchableOpacity
                style={[styles.wrapper, styles.active]}
                onPress={()=>{
                    setTouched(false)
                    //изменяем состояние конкретного варианта ответа
                    let mass=props.defAns
                    mass[props.number].variants[props.index]=false
                    props.action(mass)
                }}
            >
                <Text style={styles.ansText}>{props.contain}</Text>
            </TouchableOpacity>
            :
            <TouchableOpacity
                style={styles.wrapper}
                onPress={()=>{
                    setTouched(true)
                    //изменяем состояние конкретного варианта ответа
                    let mass=props.defAns
                    mass[props.number].variants[props.index]=true
                    props.action(mass)
                }}
            >
                <Text style={styles.ansText}>{props.contain}</Text>
            </TouchableOpacity>


    );
};

const styles = StyleSheet.create({
    wrapper: {
        width:'90%',
        borderColor:'sandybrown',
        borderWidth:3,
        borderRadius:5,
        marginTop:5,
        marginBottom:5
    },
    active: {
        backgroundColor:'lightgrey'
    },
    ansText: {
        paddingTop:5,
        paddingBottom:5,
        paddingRight:10,
        paddingLeft:10,
        fontSize:18,
        color:'sandybrown'
    }
})

export default Answer;
