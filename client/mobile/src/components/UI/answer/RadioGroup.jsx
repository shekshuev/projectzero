import React, {useEffect, useState} from 'react';
import {Text, View, StyleSheet} from 'react-native';
import {RadioButton} from 'react-native-paper';
const RadioGroup = (props) => {

    let myIndex = props.totalAnswers[props.number].variants.findIndex(i=>i===true)
    const [radio, setRadio] = useState(myIndex!==-1?myIndex:'')

    useEffect(()=>{
        setRadio(myIndex)
    }, [props.number])

    return (
        <View>
            <RadioButton.Group
                // загоняем новое значение
                onValueChange={newValue => {
                    setRadio(newValue);
                    let mass=[]
                    for (let i = 0; i < props.totalAnswers[props.number].variants.length; i++) {
                        if(i==newValue){
                            mass[i] = true
                            continue
                        }
                        mass[i] = false
                    }
                    let massTotal = props.totalAnswers
                    massTotal[props.number].variants = mass
                    props.action(massTotal)
                }}
                value={radio}
            >
                {
                    props.quizData.questions[props.number].answers.map((ans, index)=>
                        <View style={styles.radioCont} key={index}>
                            <RadioButton value={index}/>
                            <Text>{ans.text}</Text>
                        </View>
                    )
                }
            </RadioButton.Group>
        </View>
    );
};
const styles = StyleSheet.create({
    radioCont: {
        display: 'flex',
        flexDirection:'row',
        alignItems:'center'
    }
})
export default RadioGroup;
