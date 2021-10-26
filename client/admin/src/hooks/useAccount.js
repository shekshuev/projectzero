import { useSelector } from "react-redux";
import { loadAccount } from "../api/account";
import { useEffect, useState } from "react";

const useAccount = (id) => {
    const accounts = useSelector(state => state.account.accounts);
    const [account, setAccount] = useState(accounts.find(a => a.id === id));

    useEffect(() => {
        if (!account) {
            const getAccount = async () => {
                const result = await loadAccount(id);
                if (result.success) {
                    setAccount(result.payload);
                }
            };
            getAccount();
        }
    }, [])
    return [account, setAccount];
}

export default useAccount;