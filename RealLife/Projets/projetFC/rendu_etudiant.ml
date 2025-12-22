let dim = 3

type case = int * int * int

type vecteur = case

type couleur =
  | Vert
  | Jaune
  | Rouge
  | Noir
  | Bleu
  | Marron
  | Libre (*case libre du plateau*)
  | Dehors
(*case en dehors du plateau, utile pour l'affichage*)

let string_of_couleur x =
  match x with
  | Vert -> "Vert"
  | Jaune -> "Jaune"
  | Rouge -> "Rouge"
  | Noir -> "Noir"
  | Bleu -> "Bleu"
  | Marron -> "Marron"
  | Libre -> "Libre"
  | Dehors -> "Dehors"

type case_coloree = case * couleur
type configuration = case_coloree list * couleur list
type coup = Du of case * case | Sm of case list

let configuration_initial = ([], [ Vert; Jaune; Rouge; Noir; Bleu; Marron ])
let liste_joueurs (_, l) = l


let mis_a_jour_configuration _ _ = Error "To do"
let gagnant _ = Libre
let coup_possible _ _ = []

(*Q2*)
let est_dans_losange (i,j,k) dim = (i <= 2*dim && i >= -2*dim && j <= dim && k <= dim && j >= -dim && k >= -dim) && (i+j+k =0);;

est_dans_losange (-6,3,3) 3;;

(*Q3*)
let est_dans_etoile (i,j,k) dim = 
  (*Méthode avec les 2 triangles*)
  ((i >= -dim && k >= -dim && j >= -dim && i+j+k=0) || (i <= dim && k <= dim && j <= dim) && i+j+k=0);;

est_dans_etoile (-4,3,1) 3;;

let rec libre_ou_pas casecol = match casecol with
  (case,couleur) -> if couleur = Libre then Libre else couleur

(*Q18*)
let rec quelle_couleur case (casescol,_) = match List.assoc_opt case casescol with
  |Some couleur -> couleur
  |None -> Libre;;

quelle_couleur (-4,-1,5) ([((-4,-1,5),Libre);((-4,1,3),Bleu);((-6,3,3),Vert)],[Vert;Rouge;Bleu;Marron]);;

(*Q5*)
let rec tourne_case = function m -> function 
  |(i,j,k) when m=0 -> (i,j,k)
  |(i,j,k) -> tourne_case (m-1) (-k,-i,-j);; 
  (*à chaque appel récursif on décale les éléments à droites puis on calcule leur opposés*)

tourne_case 5 (-4,-1,5);;

(*Q6*)
let translate (i,j,k) (m,l,n) = (i+m, j+l, k+n);;

translate (-4,1,3) (0,2,-2);;

(*Q7*)
let diff_case (i,j,k) (l,m,n) = (i-l, j-m, k-n);;

diff_case (0,0,0) (0,-2,2)

(*Q8*)
let sont_cases_voisines = function (a,b,c) -> function 
  (*Calcul entre 1ère et 2e case : on vérifie en partant de la 1ère case (a,b,c) qu'en changeant 2 coordonnées de 1 
  et que la dernière ne change pas on a bien la 2e case (d,e,f) -> cases voisines *)
  |(d,e,f) when (a,b+1,c-1) = (d,e,f) -> true (* (0,+1,-1) *)
  |(d,e,f) when (a,b-1,c+1) = (d,e,f) -> true (* (0,-1,+1) *)
  |(d,e,f) when (a+1,b,c-1) = (d,e,f) -> true (* (+1,0,-1) *)
  |(d,e,f) when (a+1,b-1,c) = (d,e,f) -> true (* (+1,-1,0) *)
  |(d,e,f) when (a-1,b,c+1) = (d,e,f) -> true (* (-1,0,+1) *)
  |(d,e,f) when (a-1,b+1,c) = (d,e,f) -> true (* (-1,+1,0) *)
  |_ -> false;;

sont_cases_voisines (-4,3,1) (-3,3,0);;

(*Q9*)
let calcul_pivot = function (a,b,c) -> function
  |(d,e,f) when (a!=d) && (b!=e) && (c!=f) && (a+b+c=0) && (d+e+f=0) -> None (*On vérifie qu'il y a une coordonnée commune aux 2 cases*)
  |(d,e,f) when ((d+a) mod 2 = 0) && ((e+b) mod 2 =0) && ((f+c) mod 2 = 0) && (a+b+c=0) && (d+e+f=0) -> Some ((d+a)/2,(e+b)/2,(f+c)/2)
  |_ -> None;;

calcul_pivot (0,2,-2) (0,-2,2);;

exception PasDeCouple of (string)

(*Q10*)
let vec_et_dist (i1, j1, k1) (i2, j2, k2) =
  match calcul_pivot (i1, j1, k1) (i2, j2, k2) with 
  | Some _ ->  
      (* Calcul du vecteur unitaire en fonction des différences *)
      let v = 
        if i1 = i2 then 
          if j1 > j2 then (0, 1, -1) else (0, -1, 1)
        else if j1 = j2 then 
          if i1 > i2 then (1, 0, -1) else (-1, 0, 1)
        else 
          if i1 > i2 then (1, -1, 0) else (-1, 1, 0)
      in
      (* Calcul de la distance *)
      let d = (abs (i1 - i2) + abs (j1 - j2) + abs (k1 - k2)) / 2 in
      (v, d)
  | None -> raise (PasDeCouple ("Pas de couple renvoyé"));; (*Pas de couple renvoyé -> exception*)

vec_et_dist (-4,1,3) (-4,3,1);;

let rec tlaux p = function 
(*fonction auxiliaire pour tourne_liste
  permettant au tout premier élément de "se déplacer" 
  vers la fin de la liste au fur et à mesure des appels récursives*)
  |[] -> [p]
  |d :: l -> d :: tlaux p l;;

(*Q11*)
let rec tourne_liste = function
  |[] -> []
  |t::[] -> [t];
  |t::q -> tlaux t q;;

tourne_liste ["start";"8";"bit";"stop"];;

let rec der_liste = function
  |[] -> None
  |t :: [] -> Some t
  |t :: q -> der_liste q;;

der_liste ["start";"bit";"stop"];;

(*Q12*)
let rec remplir_segment m = function
  |(i,j,k) when m=0 -> []
  |(i,j,k) -> (i,j,k) :: remplir_segment (m-1) (i,j+1,k-1);;

remplir_segment 4 (-1,-3,4);;

(*Q13*)
let rec remplir_triangle_bas m = function
  |(i,j,k) when not (est_dans_etoile (i,j,k) dim) || m=0-> []
  |(i,j,k) -> remplir_segment m (i,j,k) @ remplir_triangle_bas (m-1) (i-1,j+1,k);;

remplir_triangle_bas 3 (-4,1,3);;

(*Q14*)
 let rec remplir_triangle_haut m = function 
 | (i,j,k) when not (est_dans_etoile (i,j,k) dim) || m = 0 -> []
| (i,j,k) -> remplir_segment m (i,j,k) @ remplir_triangle_haut(m-1) (i+1,j,k-1) ;;

remplir_triangle_haut 3 (4,-3,-1) ;;

(*Q15*)
let rec colorie coul = function
  |[] -> []
  |t :: q -> (t,coul) :: colorie coul q;;  

colorie Noir (remplir_triangle_haut dim (dim+1,-dim,-1))

(*Q16*)
let rec nbJoueurs = function (*fonction auxiliaire de tourne_config : calculer le nombre de joueurs*)
  |[] -> 0
  |t :: q -> 1 + nbJoueurs(q);;

nbJoueurs [Rouge;Bleu;Vert];;

let rec tcca n = function (*fonction auxiliaire de tourne_config: calcul tourne_case pour l'ensemble des cases 
                          de la liste des cases colorées selon n joueurs (couleurs)*)
  |[] -> []
  |t :: q -> match t with
      (case,couleur) -> (tourne_case (6/nbJoueurs n) case,couleur) :: tcca n q;;

tcca [1;2;3;4;5;6] [((-4,-1,5),Rouge);((-4,1,3),Bleu);((-6,3,3),Vert)];;


let tourne_config conf = match conf with 
  (lca,lcol) -> (tcca lcol lca,tourne_liste lcol);;

tourne_config ([((-4,-1,5),Rouge);((-4,1,3),Bleu);((-6,3,3),Vert)],[Vert;Rouge;Bleu;Marron]);;

(*Q17*)

let coulassoc lc lcases = 

  
let remplir_init lj dim = match nbJoueurs lj with
  |6 -> (colorie Noir (remplir_triangle_haut dim (dim+1,-dim,-1)) @ colorie Vert (remplir_triangle_bas dim (-dim-1,1,dim)) @ colorie Bleu (remplir_triangle_haut dim (-dim-1,1,dim)) @
        colorie Jaune (remplir_triangle_bas dim (dim+1,-1,-dim)) @ colorie Rouge (remplir_triangle_haut dim (-dim-1,dim,1)) @ colorie Marron (remplir_triangle_bas dim (dim+1,-1,-dim)),lj)

  |3 -> (colorie Noir (remplir_triangle_haut dim (dim+1,-dim,-1)) @ colorie Jaune (remplir_triangle_bas dim (dim+1,-1,-dim)) @ colorie Marron (remplir_triangle_bas dim (dim+1,-1,-dim)),lj)

  |2 -> (colorie Noir (remplir_triangle_haut dim (dim+1,-dim,-1)) @ (colorie Vert (remplir_triangle_bas dim (-dim-1,1,dim))),lj)
  |1 -> (colorie Vert (remplir_triangle_bas dim (-dim-1,1,dim)),lj)
  |_ -> ([],lj);;

remplir_init [Rouge,Vert,Bleu] 3;;

(*Q19*)
let rec supp cr = function (*fonction auxiliaire ajoutant les cases dans la nouvelle liste sauf celle recherchée (cr)*)
  |[]->[]
  |t :: q -> match t with
    |(case,couleur) when case<>cr -> t :: supp cr q
    |_-> supp cr q ;;

let supprime_dans_config config case = match config with
  |(ccl,coull) -> ((supp case ccl),coull);;

let conf1=([((1,2,3),Bleu);((3,2,4),Rouge);((5,3,1),Vert)],(Vert,Rouge,Bleu));;

supprime_dans_config conf1 (3,2,4)




