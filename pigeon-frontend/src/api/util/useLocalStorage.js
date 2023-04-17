import { useState, useEffect } from "react";

function useLocalState(defaultValue, key) {
    const [value, setValue] = useState(() => {
            const localStorageValue = localStorage.getItem(key);

            console.log(localStorageValue);
            return localStorageValue !== null
            ? localStorageValue
            : defaultValue;
        });

    useEffect(() => {
        localStorage.setItem(key, value);
    }, [key, value]);

    return [value, setValue];
}



export {useLocalState};