package service.imp;

import mapping.dtos.PublicInfoToyDTO;
import mapping.mappers.ToyMapper;
import model.Category;
import model.Toy;
import service.ToyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

public class ToyServiceImp implements ToyService {

    private final List<Toy> toys;
    private final ToyMapper toyMapper;

    public ToyServiceImp(){
        toyMapper = new ToyMapper();
        toys = new ArrayList<>();
    }
    @Override
    public void addToy(Toy toy) {
        Optional<Toy> toyOptional = getToyByName(toy.toyName);

        if(toyOptional.isPresent()){
            throw new RuntimeException("There is already a toy called: " + toy.toyName);
        }

        toys.add(toy);
    }

    @Override
    public void deleteToyByName(String name) {
        Optional<Toy> toyOptional = getToyByName(name);

        if(toyOptional.isEmpty()){
            throw new RuntimeException("The toy " + name + " does not exist");
        }
        toys.remove(toyOptional.get());
    }

    @Override
    public Map<Category, Integer> getToysAmountByCategory() {
        return toys.stream()
                .collect(Collectors.groupingBy(Toy::getToyCategory, Collectors.summingInt(Toy::getToyAmount)));
    }

    @Override
    public List<PublicInfoToyDTO> getAllToys() {
        return toys.stream()
                .map(toy -> toyMapper.fromEntityToDTO(toy))
                .toList();
    }

    @Override
    public int getTotalToysAmount() {
        return toys.stream()
                .map(toy-> toy.getToyAmount())
                .reduce(0,(acc,element)-> acc+element);
    }

    @Override
    public double getTotalToysPrice() {
        return toys.stream()
                .map(toy-> toy.toyPrice * toy.getToyAmount())
                .reduce(0.0,(acc,element) -> acc+element);

    }

    @Override
    public Optional<Toy> getToyByName(String name) {
        return toys.stream().filter(t-> t.toyName.equalsIgnoreCase(name)).findAny();

    }

    @Override
    public void increaseAmountToy(String toyName, int amount) {
        Optional<Toy> toyOptional = getToyByName(toyName);
        if(toyOptional.isPresent()){
            Toy toy = toyOptional.get();
            toy.setToyAmount(toy.getToyAmount() + amount);
        }else{
            throw  new RuntimeException("The toy " + toyName + " does not exist" );
        }
    }

    @Override
    public void decreaseAmountToy(String toyName, int amount) {
        Optional<Toy> toyOptional = getToyByName(toyName);
        if(toyOptional.isPresent()){
            Toy toy = toyOptional.get();
            if(toy.getToyAmount() - amount < 0 ){
                throw  new RuntimeException("The amount entered is greater than the toy's amount");
            }
            toy.setToyAmount(toy.getToyAmount() - amount);
        }else{
            throw  new RuntimeException("The toy " + toyName + " does not exist" );
        }

    }

    @Override
    public Category getMostAmountToyCategory() {
        Map<Category,Integer> amountsByCategory = getToysAmountByCategory();
        Category maxCategory = amountsByCategory.keySet().iterator().next();
        Integer maxAmount = amountsByCategory.get(maxCategory);
        for(Map.Entry<Category, Integer> map : amountsByCategory.entrySet()) {
            if(map.getValue() > maxAmount){
                maxAmount = map.getValue();
                maxCategory = map.getKey();
            }
        }
        return maxCategory;
    }

    @Override
    public Category getLessAmountToyCategory() {
        Map<Category,Integer> amountsByCategory = getToysAmountByCategory();
        Category maxCategory = amountsByCategory.keySet().iterator().next();
        Integer maxAmount = amountsByCategory.get(maxCategory);

        for(Map.Entry<Category, Integer> map : amountsByCategory.entrySet()) {
            if(map.getValue() < maxAmount){
                maxAmount = map.getValue();
                maxCategory = map.getKey();
            }
        }
        return maxCategory;
    }


    @Override
    public List<Toy> getToysWithPriceGreaterThan(double price) {
        return toys.stream().filter(toy -> toy.getToyPrice() > price).toList();
    }

    @Override
    public Map<Category,Integer> sortToysByAmountGroupedByCategory() {
        Map<Category,Integer> amountsByCategory = getToysAmountByCategory();
        return amountsByCategory.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}