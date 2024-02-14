package mapping.mappers;

import mapping.dtos.PublicInfoToyDTO;
import model.Toy;

public class ToyMapper {

        public PublicInfoToyDTO fromEntityToDTO(Toy toy){
            return new PublicInfoToyDTO(toy.toyName, toy.toyPrice);
        }
    }

