package abstraction.eq8Romu.produits;

public class ChocolatDeMarque {
	private Chocolat chocolat;
	private String marque;
	
	public ChocolatDeMarque(Chocolat chocolat, String marque) {
		if (chocolat==null) {
			throw new IllegalArgumentException("Tentative de creer une instance de ChocolatDeMarque avec null pour premier parametre");
		}
		if (marque==null) {
			throw new IllegalArgumentException("Tentative de creer une instance de ChocolatDeMarque avec null pour second parametre");
		}
		if (marque.length()<1) {
			throw new IllegalArgumentException("Tentative de creer une instance de ChocolatDeMarque avec une chaine vide pour second parametre");
		}
		this.chocolat = chocolat;
		this.marque = marque;
	}
	
	public String name() {
		return chocolat.name()+" "+marque;
	}

	public Chocolat getChocolat() {
		return chocolat;
	}

	public String getMarque() {
		return marque;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chocolat == null) ? 0 : chocolat.hashCode());
		result = prime * result + ((marque == null) ? 0 : marque.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (!(obj instanceof ChocolatDeMarque)) {
			return false;
		} else {
			ChocolatDeMarque cdmobj = (ChocolatDeMarque) obj;
			return cdmobj.getChocolat()!=null 
					&& cdmobj.getMarque()!=null 
					&& cdmobj.getChocolat().equals(this.getChocolat()) 
					&& cdmobj.getMarque().contentEquals(this.getMarque());
		}
	}
	
}
