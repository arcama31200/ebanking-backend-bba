package com.bba.ebankingbackend.services;

import com.bba.ebankingbackend.entities.AppUser;

public interface UserService {

    /**
     * Recherche un utilisateur par son nom d'utilisateur.
     *
     * @param username Le nom d'utilisateur de l'utilisateur à rechercher.
     * @return L'utilisateur trouvé, ou null s'il n'existe pas.
     */
    AppUser findByUsername(String username);

    /**
     * Enregistre un nouvel utilisateur.
     *
     * @param user L'utilisateur à enregistrer.
     * @return L'utilisateur enregistré.
     */
    AppUser save(AppUser user);

    /**
     * Met à jour les informations d'un utilisateur existant.
     *
     * @param user L'utilisateur à mettre à jour.
     * @return L'utilisateur mis à jour.
     */
    AppUser update(AppUser user);

    /**
     * Supprime un utilisateur existant.
     *
     * @param userId L'identifiant de l'utilisateur à supprimer.
     */
    void delete(Long userId);

    /**
     * Vérifie si un utilisateur avec le nom d'utilisateur donné existe déjà.
     *
     * @param username Le nom d'utilisateur à vérifier.
     * @return true si l'utilisateur existe, false sinon.
     */
    boolean existsByUsername(String username);
}
