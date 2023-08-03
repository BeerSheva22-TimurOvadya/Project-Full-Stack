import { useState } from 'react';
import { Button, FormControl, InputLabel, MenuItem, Select, TextField } from '@mui/material';
import Product from '../../model/Product';
import { Categories } from '../../model/AllCategories';

interface AddFormProps {
    submitFn: (product: Product, category: string) => void;
    productUpdated?: Product;
}

const AddForm: React.FC<AddFormProps> = ({ submitFn, productUpdated }) => {
    const [error, setError] = useState('');
    const [product, setProduct] = useState(productUpdated || { name: '', category: '', price: 0 });

    const handleInputChange = (event: any) => {
        const { name, value } = event.target;
        setProduct({ ...product, [name]: value });
    };

    const handleSubmit = () => {
        if (!product.name || !product.category || !product.price) {
            setError('Please fill in all fields');
            return;
        }

        setError('');
        submitFn(product, product.category);
        setProduct({ name: '', category: '', price: 0 });
    };

    return (
        <form
            style={{
                display: 'flex',
                flexDirection: 'column', // Сначала располагаем элементы по вертикали
                justifyContent: 'center',
                alignItems: 'center',
                position: 'absolute',
                top: '10%',
                width: '100%',
            }}
        >
            <div
                style={{
                    display: 'flex',
                    flexDirection: 'row', // Здесь располагаем элементы по горизонтали
                    justifyContent: 'center',
                    alignItems: 'center',
                    marginBottom: '20px', // Добавляем нижний отступ для пространства между полями и кнопкой
                }}
            >
                <TextField name="name" label="Name" value={product.name} onChange={handleInputChange} />
                <FormControl style={{ minWidth: '200px' }}>
                    <InputLabel id="category-label">Choose a Category</InputLabel>
                    <Select
                        labelId="category-label"
                        name="category"
                        value={product.category}
                        onChange={handleInputChange}
                        label="Choose a Category"
                    >
                        {Categories.map((category) => (
                            <MenuItem key={category} value={category}>
                                {category}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>
                <TextField
                    name="price"
                    label="Price"
                    value={product.price}
                    onChange={handleInputChange}
                    type="number"
                />
            </div>
            <Button
                style={{
                    backgroundColor: '#63a4ff',                    
                }}
                variant="contained"
                onClick={handleSubmit}
            >
                Next Step
            </Button>
            {error && <div style={{ color: 'red' }}>{error}</div>}
        </form>
    );
};

export default AddForm;
