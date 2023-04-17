import { useLocalState } from "../../api/util/useLocalStorage";

const HandleLogin = () => {
    const [jwt, setJwt] = useLocalState("", "jwt");
    const [storedId, setStoredId] = useLocalState("", "userId");
    const [error, setError] = useState("");
    
    const handleSubmit = async () => {
        if (!username || !password) {
        setError("Please enter username and password");
        } else {
        await api.post('http://127.0.0.1:8080/login', {
            username: username,
            password: password
        })
        .then((res) => {
            if (res.status == 200 || res.data == {}) {
            setJwt(res.headers.get("authorization").replace(/['"]+/g, ''));
            setStoredId(res.data.replace(/['"]+/g, ''));
            } else {
            console.log(res);
            setError('Please make sure you\'ve entered the correct username and password.');
            }
        })
        .catch ((e) => {
            console.log(e);
            setError('Please make sure you\'ve entered the correct username and password.');
        }
        );
        // .error('Something\'s gone wrong. Please contact the developer or try again later.');
        }
    }

}

export default HandleLogin;