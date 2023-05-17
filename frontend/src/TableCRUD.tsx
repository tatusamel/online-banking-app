import {
    List,
    ListItem,
    Button,
  } from "@chakra-ui/react";
  
  export const TableCRUD = () => {
    const handleClick = (item: any) => {
      alert(`Clicked: ${item}`);
    };
  
    const items = ["Insert", "Update", "Delete"];
  
    return (
      <List spacing={2}>
        {items.map((item) => (
          <ListItem key={item}>
            <Button
              variant="ghost"
              width="100%"
              textAlign="left"
              onClick={() => handleClick(item)}
            >
              {item}
            </Button>
          </ListItem>
        ))}
      </List>
    );
  };
  