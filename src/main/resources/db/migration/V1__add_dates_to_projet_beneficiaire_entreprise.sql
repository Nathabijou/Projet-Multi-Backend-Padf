-- Add date_creation and date_maj columns to projet_beneficiaire_entreprise table
ALTER TABLE projet_beneficiaire_entreprise
    ADD COLUMN IF NOT EXISTS date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE projet_beneficiaire_entreprise
    ADD COLUMN IF NOT EXISTS date_maj TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
