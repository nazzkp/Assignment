import com.google.gson.annotations.SerializedName

data class MedicineResponse(
    val problems: List<Problem>
)

data class Problem(
    val name: String,
    val details: Details?
)

data class Details(
    val medications: List<Medication>?,
    val labs: List<Lab>?
)

data class Medication(
    val medicationClasses: List<MedicationClass>?
)

data class MedicationClass(
    val className: String?,
    val associatedDrugs: List<AssociatedDrug>?
)

data class AssociatedDrug(
    val name: String?,
    val dose: String?,
    val strength: String?
)

data class Lab(
    @SerializedName("missing_field")
    val missingField: String?
)