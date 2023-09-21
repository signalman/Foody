import { useState } from 'react';

function useToggle(initial: boolean): [boolean, () => void] {
	const [toggle, setToggle] = useState<boolean>(initial);

	const onToggle = () => {
		const next = !toggle;
		setToggle(next);
	};

	return [toggle, onToggle];
}

export default useToggle;
