import { useState, useEffect } from "react";

function useLocalState(defaultValue, key) {
    const [value, setValue] = useState(() => {
            const localStorageValue = localStorage.getItem(key);

            return localStorageValue !== null
            ? JSON.parse(localStorageValue)
            : defaultValue;
        });

    useEffect(() => {
        localStorage.setItem(key, value);
        console.log('setting item', key, value);
    }, [key, value]);

    return [value, setValue];
}



export {useLocalState};