package business.model;

import java.io.Serializable;

public interface IEntity<PK extends Serializable> extends Serializable {

	PK getId();

}
