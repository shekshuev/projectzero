import { StyleSheet, Text, View } from 'react-native';
import AppRouter from "./src/components/AppRouter";
import React from "react";
import {registerRootComponent} from 'expo';

export default function App() {
  return (
    <View style={styles.container}>
      <AppRouter/>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
registerRootComponent(App);
