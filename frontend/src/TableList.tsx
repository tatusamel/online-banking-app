import {
    Text,
    List,
    ListItem,
    Button,
    Container,
  } from "@chakra-ui/react";
  
  export const TableList = () => {
    const handleClick = (item: any) => {
      alert(`Clicked: ${item}`);
    };
  
    const items = ["Item 1", "Item 2", "Item 3", "Item 4", "Item 5"];
  
    for (let index = 0; index < 100; index++) {
      items.push("Item" + index);
    }
  
    return (
    <Container textAlign="center"  height="100%" >
        <Text height="10%">Tables</Text>
      <List height="90%" spacing={2} overflow="auto" marginTop="10%">
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
      </Container>
    );
  };
  