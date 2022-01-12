import React, { useState, useEffect } from 'react';
import {Text, View} from 'react-native';
import * as Location from 'expo-location';

const Geopos = () => {
    const [location, setLocation] = useState(null);
    const [errorMsg, setErrorMsg] = useState(null);
    const [text, setText] = useState(null);
    useEffect(() => {
        (async () => {
            let { status } = await Location.requestForegroundPermissionsAsync();
            if (status !== 'granted') {
                console.log('OH SHHHHHHHHHHHIT')
                setErrorMsg('Permission to access location was denied');
            }
            else {
                let {loc}= await Location.getCurrentPositionAsync({});
                setLocation(loc);
                setText(loc)
                console.log(loc)
            }
        })();
    }, []);

    return (
        <View style={{margin:50}}>
            <Text>{text}</Text>
            <Text>lol</Text>
        </View>
    );
}

export default Geopos;
